package uk.ac.cranfield.bix.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeExpression;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeGff;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeSequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeTranscriptomicCov;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeVcf;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeVcfCoverageGenomics;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.SortToBins;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.VCFDepthExtract;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.VCFLineParser;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Transcriptomic.CoverageParser;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Transcriptomic.GffParser2;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Transcriptomic.SortToBinsTranscriptomics;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.DiffJavascriptWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.EbSeqParser;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.EbseqData;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.GffParserExpression;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.gffSorter;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffParser;
import static uk.ac.cranfield.bix.utilities.fileParser.RSEMBamExpression.RsemGenesResultsParser;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VCFHistParser;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VcfToolsSNPDensity;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.VcfToolsSNPS;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.fastaParser;

@Service
public class PathFinder {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    //current path THE ONE OF TWO TO CHANGE AT DIFFERENT COMPUTERS
    private final String constantPath = ("Z:/ProfileData/s260592/Desktop/WebCircos/");
    private String currentPath;

    public PathFinder() {
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getSimpeUserPath() {
        return constantPath + "user/";
    }

    public String getEntireFilePathNotLogged() {
        String userID, path;
        File dir1, dir1_5, dir2;

//        System.out.println("User is ANONYMOUS");
        userID = RequestContextHolder.currentRequestAttributes().getSessionId();
        //newPath with added userID
        path = constantPath + "temp/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }

        return path;
    }

    public String getEntireFilePathLogged() {
        String userID, path;
        File dir1, dir1_5, dir2;

//        System.out.println("User is LOGGED");
        userID = SecurityContextHolder.getContext().getAuthentication().getName();
        //newPath with added userID
        path = constantPath + "user/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }

        return path;
    }

    public String getEntireFilePathLogged(String projectName) {
        String userID, path;
        File dir1, dir2;

//        System.out.println("User is LOGGED");
        userID = SecurityContextHolder.getContext().getAuthentication().getName();
        //newPath with added userID
        path = constantPath + "user/" + userID;
        dir1 = new File(path);
        //creating new directory for user if doesn't exist
        if (!dir1.exists()) {
            dir1.mkdir();
        }
        //newPath with added projectName
        path = path + "/" + projectName;
        //creating new directory for project if doesn't exist
        dir2 = new File(path);
        if (!dir2.exists()) {
            dir2.mkdir();
        }
        return path;
    }

//method to get the content of the file content of folder for unloged user if not empty return the path of the gff file
// method to know if a gff file has alredy been uploaded for loged user. If exists, return the path of the gff file 
    public String getGffFilePath(String projectName) throws IOException {
        String gffFilePath = "";
        //Find user
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            //Check if content of folder is empty
            String entireFilePathNotLogged = getEntireFilePathNotLogged() + "/annotation/contentOfFolder.txt";
            String filePath = checkIfFileIsEmpty(entireFilePathNotLogged);
            if(!filePath.isEmpty()){
                gffFilePath = filePath;
            }

        } else {
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //Find project
            Project project = projectService.findByProjectName(projectName, user);

            //Find file by file type.
            FileInput findByFileType = fileService.findByFileType("annotation", project);

            if (findByFileType != null) {
                gffFilePath = findByFileType.getF_path();
            }

        }

        return gffFilePath;
    }

    public String checkIfFileIsEmpty(String filePath) throws FileNotFoundException, IOException {
        File f = new File(filePath);
         String line = "";
        if (f.exists()) {
            FileReader r = new FileReader(f);
            BufferedReader br = new BufferedReader(r);    
            line = br.readLine();
            if(line != null &&
                    !line.equals("")){                
               line = line.trim();
            }
            r.close();
        }
        
        return line;
    }
    
public void parseFile(String filePath, String fileType, String projectName) throws IOException, ClassNotFoundException, InterruptedException {
        String fileWithoutExtension = filePath.substring(0, filePath.lastIndexOf("."));
        switch (fileType) {
            case "sequence":
                //Parse and Serialize fasta file (only FASTA file)
                List<Sequence> fastaParser = fastaParser(filePath);
                SerializeSequence(fastaParser, fileWithoutExtension + ".txt");
                break;
            case "annotation":
                //Genes (only ANNOTATION file)
                List<String[]> GffParser = GffParser(filePath);
                SerializeGff(GffParser, fileWithoutExtension + ".txt");

                break;

            case "bedcov":
                String gffFilePath = this.getGffFilePath(projectName);
                //Transcriptomic Coverage (GFF and BEDCOV files)
                List<List<String[]>> listTranscriptomicCoverage = GffParser2(gffFilePath);
                ArrayList<Object[]> coverageData = CoverageParser(listTranscriptomicCoverage, filePath);
                ArrayList<Object[]> sortedBinsTCov = SortToBinsTranscriptomics(listTranscriptomicCoverage, coverageData);
                SerializeTranscriptomicCov(sortedBinsTCov, fileWithoutExtension + "transcriptomicCov.txt");

                break;

            case "difExpression":
                String gffPath = this.getGffFilePath(projectName);
                //Gene DE (GFF and EBSEQ files)
                List<List<String[]>> genesAndMetadataForDExpr = GffParserExpression(gffPath);
                ArrayList<String[]> ebseqData = EbSeqParser(filePath);
                ArrayList<String[]> sortedgff = gffSorter(genesAndMetadataForDExpr);
                ArrayList<Object[]> data = EbseqData(genesAndMetadataForDExpr, sortedgff);
                List<HeatMapDataPoint> dataToSerialize = DiffJavascriptWriter(ebseqData, data, "differential Expression");
                SerializeExpression(dataToSerialize, fileWithoutExtension + "DExpression.txt");

                break;

            case "expression":

                //Gene Expression (GFF and RSEM files)
                String gFFPath = this.getGffFilePath(projectName);
                List<List<String[]>> genesAndMetadataForExpr = GffParserExpression(gFFPath);
                ArrayList<String[]> ExprData = RsemGenesResultsParser(filePath);
                ArrayList<String[]> sortedgffExpr = gffSorter(genesAndMetadataForExpr);
                ArrayList<Object[]> dataExpr = EbseqData(genesAndMetadataForExpr, sortedgffExpr);
                List<HeatMapDataPoint> dataToSerializeExpr = DiffJavascriptWriter(ExprData, dataExpr, "Expression");
                SerializeExpression(dataToSerializeExpr, fileWithoutExtension + "Expression.txt");

                break;

            case "variants":
                // (only VCF file)
                String VcfToolsSNPS = VcfToolsSNPS(filePath);
                String VcfToolsSNPDensity = VcfToolsSNPDensity(VcfToolsSNPS);
                ArrayList<String[]> VCFHistParser = VCFHistParser(VcfToolsSNPDensity);
                SerializeVcf(VCFHistParser, fileWithoutExtension + ".txt");

                //Change the path of the serialized data to put the path to genomic coverage folder. 
                List<List<String[]>> lists = VCFLineParser(filePath);
                ArrayList<Object[]> depth = VCFDepthExtract(lists);
                ArrayList<Object[]> sortedBins = SortToBins(lists, depth);
                SerializeVcfCoverageGenomics(sortedBins, fileWithoutExtension + "coverage.txt");
                break;

            default:
                break;

        }
    }

}
