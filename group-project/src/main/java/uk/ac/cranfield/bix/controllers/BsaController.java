/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.BsaInput;
import uk.ac.cranfield.bix.controllers.rest.BsaOutPut;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LinkData;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Link;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import uk.ac.cranfield.bix.services.PathFinder;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.BSADataWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.BSALinkWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.BedToolsIntersect;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.BinMerger;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.Comparator;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.SortToBinsIntersectedFiles;
import static uk.ac.cranfield.bix.utilities.fileParser.BSA_Parsers.VCFParser;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.VCFLineParser;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VcfToolsSNPS;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.createBiocircosGenomeObject;

/**
 * This class allow to create BSA output data so they can be drawn on the web
 * app. Sequence and annotation are serialised object but links need to be
 * created each time because the 4 files are needed.
 *
 * @author vmuser
 */
@Controller
public class BsaController {

    @Autowired
    private PathFinder pathFinder;

    /**
     *
     * @param bsaInput
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "/bsa.data", method = RequestMethod.POST)
    public @ResponseBody
    BsaOutPut sendBsaData(@RequestBody BsaInput bsaInput) throws IOException, ClassNotFoundException {

        bsaInput.removeExtensions();
        String path;
        BsaOutPut bsaOutput = new BsaOutPut();

        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            path = pathFinder.getEntireFilePathNotLogged() + "/";
        } else {
            path = pathFinder.getEntireFilePathLogged(bsaInput.getProjectName());

        }

        if (new File(path + "/sequence/" + bsaInput.getSequence() + ".txt").exists()) {

            List<Sequence> seq = (List<Sequence>) Deserialize(path + "/sequence/" + bsaInput.getSequence() + ".txt");
            List<Object[]> obj = createBiocircosGenomeObject(seq);

            bsaOutput.setGenomes(obj);
        }

        if (new File(path + "/annotation/" + bsaInput.getAnnotation() + ".txt").exists()) {
            List<GffDataPoint> gff = (List<GffDataPoint>) Deserialize(path + "/annotation/" + bsaInput.getAnnotation() + ".txt");
            IndGff GffWriter = GffWriter(gff);
            bsaOutput.setArc(GffWriter);
        }

        if (new File(path + "/variants/" + bsaInput.getParent1()).exists() && new File(path + "/variants/" + bsaInput.getParent2()).exists() && new File(path + "/variants/" + bsaInput.getPool1()).exists() && new File(path + "/variants/" + bsaInput.getPool2()).exists()) {

            //Intersect pool1 and pool2
            String pool1And2Intersect = BedToolsIntersect(path + "/variants/" + bsaInput.getPool1() + ".recode.vcf", path + "/variants/" + bsaInput.getPool2() + ".recode.vcf");
            //Intersect pool2 and pool1
            String pool2And1Intersect = BedToolsIntersect(path + "/variants/" + bsaInput.getPool2() + ".recode.vcf", path + "/variants/" + bsaInput.getPool1() + ".recode.vcf");
            //parse intersected file
            List<List<String[]>> lists1vs2 = VCFLineParser(pool1And2Intersect);
            List<List<String[]>> lists2vs1 = VCFLineParser(pool2And1Intersect);
            //parse parent files
            List<List<String[]>> parent1 = VCFLineParser(path + "/variants/" + bsaInput.getParent1());
            List<List<String[]>> parent2 = VCFLineParser(path + "/variants/" + bsaInput.getParent2());
            //sort intersected files
            ArrayList VCFParser1vs2 = VCFParser(lists1vs2);
            ArrayList SortToBinsIntersectedFiles1vs2 = SortToBinsIntersectedFiles(VCFParser1vs2, parent1, 100000);
            ArrayList VCFParser2vs1 = VCFParser(lists2vs1);
            ArrayList SortToBinsIntersectedFiles2vs1 = SortToBinsIntersectedFiles(VCFParser2vs1, parent1, 100000);
            //sort parents
            ArrayList VCFParserParent1 = VCFParser(parent1);
            ArrayList SortToBinsIntersectedFilesP1 = SortToBinsIntersectedFiles(VCFParserParent1, parent1, 100000);
            ArrayList VCFParserParent2 = VCFParser(parent2);
            ArrayList SortToBinsIntersectedFilesP2 = SortToBinsIntersectedFiles(VCFParserParent2, parent2, 100000);
            //Comparison
            ArrayList Comparator = Comparator(SortToBinsIntersectedFilesP1, SortToBinsIntersectedFilesP2, SortToBinsIntersectedFiles1vs2, SortToBinsIntersectedFiles2vs1);
            //merging
            ArrayList BinMerger = BinMerger(Comparator, 100000);
            //write link data
            List BSADataWriter = BSADataWriter(BinMerger);
            //create Link
            Link link01 = BSALinkWriter(BSADataWriter);
            bsaOutput.setLink(link01);

        }

        return bsaOutput;

    }

}
