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
    
    @RequestMapping(value = "/import/getAll", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse getAllFiles(@RequestParam("projectName") String projectName)
    {
        Project project;
        String result = "";
        List<FileInput> allFiles;
        int i, j;
                
        //Find user
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userLogin);

        //Find project
        project = projectService.findByProjectName(projectName, user);
        
        //Find files
        allFiles = fileService.findAll(project);
        for(j=0;j<allFiles.size();j++)
            result = result + allFiles.get(j).getF_name() + "\t";
        return new RestResponse(null, result);
    };
    
    @RequestMapping(value = "/import/copyFile", method = RequestMethod.POST)
    public
    @ResponseBody
    RestResponse copyFile(@RequestParam("newProjectName") String newProjectName, @RequestParam("oldProjectName") String oldProjectName, @RequestParam("oldFileName") String oldFileName) throws Exception
    {
        String oldPath, newPath, fileType, newFileName="", newExtention, oldExtention, finalExtention="", oldFileNameWE, finalName = null;
        Project oldProject, newProject;
        FileInput oldFile, newFile;
        File dir, oldDir, fileToCopy;
        File[] oldFilesList;
        int i;
        boolean isTxtCopied = false, isOriginalFileCopied=false;
        
        try
        {
            newPath = pathFinder.getEntireFilePathLogged(newProjectName);
            oldPath = pathFinder.getEntireFilePathLogged(oldProjectName);

            //Find user
            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userLogin);

            //Check if project allready exist
            oldProject = projectService.findByProjectName(oldProjectName, user);
            newProject = projectService.findByProjectName(newProjectName, user);

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
            newPath = newPath + "/" + fileType;
            oldPath = oldPath + "/" + fileType;
            //get all files
            oldDir = new File(oldPath);
            oldFilesList = oldDir.listFiles();
            
            //iterate through files
            for (i=0; i<oldFilesList.length; i++)
            {
                fileToCopy = oldFilesList[i];
                //check if filename matches
                newFileName = fileToCopy.getName();
                String[] splittedNewFileName = newFileName.split("\\.");
                newExtention = splittedNewFileName[splittedNewFileName.length-1];
                if ("sorted".equals(newExtention))
                    newExtention=splittedNewFileName[splittedNewFileName.length-2]+"."+newExtention;
                oldExtention = oldFileName.split("\\.")[oldFileName.split("\\.").length-1];
                if ("sorted".equals(oldExtention))
                    oldExtention = oldExtention + "." + oldFileName.split("\\.")[oldFileName.split("\\.").length-2];
                oldFileNameWE = oldFileName.substring(0, (oldFileName.length()-oldExtention.length()-1));

                newFileName = newFileName.replaceAll(newExtention, "");
                if (newFileName.startsWith(oldFileNameWE))
                {
                    //copy all files
                    if (!newExtention.equals("txt") && isTxtCopied == false && !newExtention.equals("snpden"))
                    {
                        FileCopyUtils.copy(new FileInputStream(oldPath + "/" + oldFileName), new FileOutputStream(newPath + "/" + newFileName + newExtention));
                        isTxtCopied = true;
                        finalName = newFileName+ newExtention;
                    }
                    else
                    {
                        if ("sequence".equals(fileType) && isOriginalFileCopied == false)
                        {
                            List<Sequence> listSequence = (List<Sequence>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeSequence(listSequence, newPath + "/" + newFileName + newExtention);
                            isOriginalFileCopied = true;
                        }
                        else if ("annotation".equals(fileType) && isOriginalFileCopied == false)
                        {
                            List<GffDataPoint> listGFF = (List<GffDataPoint>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeGffDataPOint(listGFF, newPath + "/" + newFileName + newExtention);
                            isOriginalFileCopied = true;
                        }
                        else if ("bedcov".equals(fileType) && isOriginalFileCopied == false)
                        {
                            List<LineDataPoint> listBedcov = (List<LineDataPoint>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeTranscriptomicCov (listBedcov, (newPath + "/" + newFileName + newExtention));
                            isOriginalFileCopied = true;
                        }
                        else if ("expression".equals(fileType) && isOriginalFileCopied == false)
                        {
                            List<HeatMapDataPoint> listExpression = (List<HeatMapDataPoint>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeExpression(listExpression, newPath + "/" + newFileName + newExtention);
                            isOriginalFileCopied = true;
                        }
                        else if ("difExpression".equals(fileType) && isOriginalFileCopied == false)
                        {
                            List<HeatMapDataPoint> listExpression = (List<HeatMapDataPoint>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeExpression(listExpression, newPath + "/" + newFileName + newExtention);
                            isOriginalFileCopied = true;
                        }
                        else if ("variants".equals(fileType) && isOriginalFileCopied == false)
                        {
                            if (newFileName.endsWith("<<."))
                                newFileName=newFileName.substring(0,newFileName.length()-1);
                            //no extention
                            List<HistogramDataPoint> HistogramData = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileName + newExtention);
                            SerializeVcf(HistogramData, (newPath+ "/" + newFileName + "txt"));
                            //chrom
                            List<HistogramDataPoint> HistogramDataChrom = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileName + "chrom.txt");
                            SerializeVcf(HistogramDataChrom, (newPath + "/" + newFileName.substring(0,(newFileName.length()-1)) + "chrom.txt"));
                            //coverage
                            List<HistogramDataPoint> sortedBins = (List<HistogramDataPoint>) Deserialize(oldPath + "/" + newFileName + "coverage.txt");
                            SerializeVcfCoverageGenomics(sortedBins, (newPath + "/" + newFileName + "coverage.txt"));
                            
                            isOriginalFileCopied = true;
                        }
                    }
                }
            }
            
            newFile = new FileInput();
            newFile.setF_name(finalName);
            newFile.setF_path(newPath + "/" + finalName);
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
    @SuppressWarnings("empty-statement")
    private boolean checkIfGFF(Project project) throws Exception
    {
        boolean flag =false;
        List<FileInput> findAll = fileService.findAll(project);

        for (FileInput file : findAll)
        {
            String fileType = file.getF_type();
            if ("annotation".equals(fileType))
                flag = true;
        };
        return flag;
    }
}