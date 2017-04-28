package uk.ac.cranfield.bix.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.RestResponse;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import uk.ac.cranfield.bix.models.FileInput;
import uk.ac.cranfield.bix.models.Project;
import uk.ac.cranfield.bix.models.User;
import uk.ac.cranfield.bix.services.FileService;
import uk.ac.cranfield.bix.services.PathFinder;
import uk.ac.cranfield.bix.services.ProjectService;
import uk.ac.cranfield.bix.services.UserService;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeExpression;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeGffDataPOint;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeSequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeTranscriptomicCov;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeVcf;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.SerializeVcfCoverageGenomics;

@Controller
public class ImportFromDatabaseController 
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private PathFinder pathFinder;

    /**
     * getAllFiles returns all files for project
     * @param projectName String with project name
     * @return ResponseBody with files list or error
     */
    @RequestMapping(value = "/import/getAll", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse getAllFiles(@RequestParam("projectName") String projectName)
    {
        Project project;
        String result = "";
        List<FileInput> allFiles;
        int j;
                
        //find user
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userLogin);

        //find project
        project = projectService.findByProjectName(projectName, user);
        
        //find files
        allFiles = fileService.findAll(project);
        for(j=0;j<allFiles.size();j++)
            result = result + allFiles.get(j).getF_name() + "\t";
        return new RestResponse(null, result);
    };


    /**
     * copyFile copies desired file with parsed data from old project to new one
     * @param newProjectName String with project name to copy file into
     * @param oldProjectName String with project name to copy file from
     * @param oldFileName String with file name to copy
     * @return RestResponse with null or error
     * @throws Exception
     */
    @RequestMapping(value = "/import/copyFile", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse copyFile(@RequestParam("newProjectName") String newProjectName, @RequestParam("oldProjectName") String oldProjectName, @RequestParam("oldFileName") String oldFileName) throws Exception
    {
        String oldPath, newPath, fileType, newFileName="", newExtention, oldExtention, finalExtention="", oldFileNameWE, finalName = null, newFileNameWE;
        Project oldProject, newProject;
        FileInput oldFile, newFile;
        File dir, oldDir, fileToCopy;
        File[] oldFilesList;
        int i;
        boolean isTxtCopied = false, isOriginalFileCopied=false;
        
        try
        {
            //get paths
            newPath = pathFinder.getEntireFilePathLogged(newProjectName);
            oldPath = pathFinder.getEntireFilePathLogged(oldProjectName);

            //find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //get projects
            oldProject = projectService.findByProjectName(oldProjectName, user);
            newProject = projectService.findByProjectName(newProjectName, user);

            //get files
            oldFile = fileService.getByName(oldFileName, oldProject);

            //check if gff
            fileType = oldFile.getF_type();
            if ("annotation".equals(fileType))
            {
                if (checkIfGFF(newProject))
                {
                    List<FileInput> findAll = fileService.findAll(newProject);
                    for (FileInput file : findAll)
                    {
                        if ("annotation".equals(fileType))
                            fileService.delete(file);
                        else if ("difExpression".equals(fileType))
                            fileService.delete(file);
                        else if ("expression".equals(fileType))
                            fileService.delete(file);
                        else if ("bedcov".equals(fileType))
                            fileService.delete(file);
                        else
                        {}
                    };
                    new File(newPath+"/annotation").delete();
                    new File(newPath+"/difExpression").delete();
                    new File(newPath+"/expression").delete();
                    new File(newPath+"/bedcov").delete();
                    dir = new File(newPath + "/" + fileType);
                    if (!dir.exists())
                        dir.mkdir();
                }
            }
            //add file type to paths
            newPath = newPath + "/" + fileType;
            oldPath = oldPath + "/" + fileType;
            //get all files
            oldDir = new File(oldPath);
            oldFilesList = oldDir.listFiles();
            
            //iterate through files
            for (i=0; i<oldFilesList.length; i++)
            {
                //get file to copy
                fileToCopy = oldFilesList[i];

                //get both file names
                newFileName = fileToCopy.getName();
                //olFileName

                //get both file extensions
                newExtention = newFileName.split("\\.")[newFileName.split("\\.").length-1];
                if ("sorted".equals(newExtention))
                    newExtention = newFileName.split("\\.")[newFileName.split("\\.").length-2] + "." + newExtention;
                oldExtention = oldFileName.split("\\.")[oldFileName.split("\\.").length-1];
                if ("sorted".equals(oldExtention))
                    oldExtention = oldFileName.split("\\.")[oldFileName.split("\\.").length-2] + "." + oldExtention;

                //get both file names without extensions
                newFileNameWE = newFileName.substring(0,(newFileName.length() - newExtention.length() - 1));
                oldFileNameWE = oldFileName.substring(0,(oldFileName.length() - oldExtention.length() - 1));

                
                //check if filename matches
                if (newFileNameWE.startsWith(oldFileNameWE))
                {
                    //copy all files

                    //original file
                    if ((!newExtention.equals("txt") && !newExtention.equals("snpden")))
                    {
                        FileCopyUtils.copy(new FileInputStream(oldPath + "/" + oldFileName), new FileOutputStream(newPath + "/" + newFileNameWE + "." + newExtention));
                    }
                    else
                    {
                        //deserialize and serialize binary files
                        if ("sequence".equals(fileType) && isTxtCopied == false)
                        {
                            List<Sequence> listSequence = (List<Sequence>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                            SerializeSequence(listSequence, newPath + "/" + newFileNameWE + "." + newExtention);
                            isTxtCopied = true;
                        }
                        else if ("annotation".equals(fileType) && isTxtCopied == false)
                        {
                            List<GffDataPoint> listGFF = (List<GffDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                            SerializeGffDataPOint(listGFF, newPath + "/" + newFileNameWE + "." + newExtention);
                            isTxtCopied = true;
                        }
                        else if ("bedcov".equals(fileType) && isTxtCopied == false)
                        {
                            List<LineDataPoint> listBedcov = (List<LineDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                            SerializeTranscriptomicCov (listBedcov, (newPath + "/" + newFileNameWE + "." + newExtention));
                            isTxtCopied = true;
                        }
                        else if ("expression".equals(fileType) && isTxtCopied == false)
                        {
                            List<HeatMapDataPoint> listExpression = (List<HeatMapDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                            SerializeExpression(listExpression, newPath + "/" + newFileNameWE + "." + newExtention);
                            isTxtCopied = true;
                        }
                        else if ("difExpression".equals(fileType) && isTxtCopied == false)
                        {
                            List<HeatMapDataPoint> listExpression = (List<HeatMapDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                            SerializeExpression(listExpression, newPath + "/" + newFileNameWE + "." + newExtention);
                            isTxtCopied = true;
                        }
                        else if ("variants".equals(fileType) && isTxtCopied == false)
                        {
                            if (newFileNameWE.equals(oldFileNameWE))
                            {
                                if (newFileName.endsWith("\\."))
                                    newFileName=newFileName.substring(0,newFileName.length()-1);
                                //no extention
                                List<HistogramDataPoint> HistogramData = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "." + newExtention);
                                SerializeVcf(HistogramData, (newPath+ "/" + newFileNameWE + ".txt"));
                                //chrom
                                List<HistogramDataPoint> HistogramDataChrom = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "chrom.txt");
                                SerializeVcf(HistogramDataChrom, (newPath + "/" + newFileName.substring(0,(newFileName.length()-1)) + "chrom.txt"));
                                //coverage
                                List<HistogramDataPoint> sortedBins = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileNameWE + "coverage.txt");
                                SerializeVcfCoverageGenomics(sortedBins, (newPath + "/" + newFileNameWE + "coverage.txt"));
                
                                isTxtCopied = true;
                            }
                        }
                    }
                }
            }
            //save file in database
            newFile = new FileInput();
            newFile.setF_name(oldFileName);
            newFile.setF_path(newPath + "/" + oldFileName);
            newFile.setF_type(fileType);
            newFile.setProject(newProject);
        
            fileService.save(newFile);
            return new RestResponse(null, null);
        }
        catch(Exception e)
        {
            return new RestResponse("Copying error", e.getMessage());
        }
    }


    /**
     * chceckIfGFF return true if annotation file already exists in project
     * @param project Project in which we look for annotation file
     * @return true if there is annotation file and false if there is not
     * @throws Exception
     */
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(Project project) throws Exception
    {
        boolean flag =false;
        //get all files
        List<FileInput> findAll = fileService.findAll(project);

        //iterate through all files
        for (FileInput file : findAll)
        {
            //get file type
            String fileType = file.getF_type();
            //raise a flag if annotation file already exists for this project
            if ("annotation".equals(fileType))
                flag = true;
        };
        return flag;
    }
}