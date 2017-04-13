package uk.ac.cranfield.bix.utilities;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
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

public class Utilities {

    public static String hashPassword(String password) {
        try {
            // Hashing password
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(password.getBytes("UTF-8"));
            // Encoding the hash back into a string
            StringBuilder hashedPassword = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hashedPassword.append('0');
                }
                hashedPassword.append(hex);
            }
            return hashedPassword.toString();
        } catch (Exception ex) {
            System.out.println("Hashing algorithm not failed.");
        }
        return null;
    }

    public static String greetingMessage() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "Welcome " + user.getName() + "!";
        } else {
            return "Welcome!";
        }
    }

    /**
     * Validate user email address before registering user in DB
     *
     * @param email user email
     * @return true if the email address is valid otherwise false
     */
    public static boolean emailValidator(String email) {

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();

    }

    public static void parseFile(String filePath, String fileType) throws IOException, ClassNotFoundException, InterruptedException {
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

                //                Gene Expression (GFF and RSEM files)
                List<List<String[]>> genesAndMetadataForExpr = GffParserExpression(filePath);
                ArrayList<String[]> ExprData = RsemGenesResultsParser(filePath+"/Downloads/Downloads.genes.results");
                ArrayList<String[]> sortedgffExpr = gffSorter(genesAndMetadataForExpr);
                ArrayList<Object[]> dataExpr = EbseqData(genesAndMetadataForExpr, sortedgffExpr);
                List<HeatMapDataPoint> dataToSerializeExpr = DiffJavascriptWriter(ExprData, dataExpr, "Expression");
                SerializeExpression(dataToSerializeExpr, fileWithoutExtension + "Expression.txt");

                //Transcriptomic Coverage (GFF and BEDCOV files)
                List<List<String[]>> listTranscriptomicCoverage = GffParser2(filePath);
                ArrayList<Object[]> coverageData = CoverageParser(listTranscriptomicCoverage, filePath+"/bedfiles/MT_Leaf12.bedcov");
                ArrayList<Object[]> sortedBinsTCov = SortToBinsTranscriptomics(listTranscriptomicCoverage, coverageData);
                SerializeTranscriptomicCov(sortedBinsTCov, fileWithoutExtension + "transcriptomicCov.txt");

                //                Gene DE (GFF and EBSEQ files)
                List<List<String[]>> genesAndMetadataForDExpr = GffParserExpression(filePath);
                ArrayList<String[]> ebseqData = EbSeqParser(filePath+"/Downloads/GeneMat.results.sorted");
                ArrayList<String[]> sortedgff = gffSorter(genesAndMetadataForDExpr);
                ArrayList<Object[]> data = EbseqData(genesAndMetadataForDExpr, sortedgff);
                List<HeatMapDataPoint> dataToSerialize = DiffJavascriptWriter(ebseqData, data, "differential Expression");
                SerializeExpression(dataToSerialize, fileWithoutExtension + "DExpression.txt");

                break;

            case "bedcov":

                break;

            case "difExpression":

                break;

            case "expression":

                break;

            case "variants":
                // (only VCF file)
                String VcfToolsSNPS = VcfToolsSNPS(filePath);
                String VcfToolsSNPDensity = VcfToolsSNPDensity(VcfToolsSNPS);
                ArrayList<String[]> VCFHistParser = VCFHistParser(VcfToolsSNPDensity);
                SerializeVcf(VCFHistParser, fileWithoutExtension + ".txt");

                List<List<String[]>> lists = VCFLineParser(filePath);
                ArrayList<Object[]> depth = VCFDepthExtract(lists);
                ArrayList<Object[]> sortedBins = SortToBins(lists, depth);
                SerializeVcfCoverageGenomics(sortedBins, fileWithoutExtension + "coverage.txt");
                break;

            default:
                break;

        }
    }

    public static boolean isUserAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a folder with this name already exist for this user.
     *
     * @return if the folder exist or not.
     */
    public static boolean folderExist(String folderPath) {
        File userFolder = new File(folderPath);
        return userFolder.exists();
    }

}
