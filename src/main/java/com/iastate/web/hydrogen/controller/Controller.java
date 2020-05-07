package com.iastate.web.hydrogen.controller;

import com.iastate.web.hydrogen.service.DockerService;
import com.iastate.web.hydrogen.util.FileUtil;
import com.spotify.docker.client.exceptions.DockerException;
import model.Graph;
import model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    @Autowired
    private DockerService dockerService;
    @Value("${file.upload.directory}")
    private String uploadDirectory;
    @Value("${docker.test.directory}")
    private String hydrogenTestDirectory;
    @Value("${hydrogen.output.directory}")
    private String testOutputDirectory;

    @GetMapping
    public String getHome(Model model) {
        return "index";
    }

    @ResponseBody
    @GetMapping("mvicfg/json/{versionId}")
    public String getMVICFGInJson(@PathVariable String versionId, Model model) {
        try {
            return FileUtil.readFile(this.testOutputDirectory+"/"+versionId+"/MVICFG.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/mvicfg/download/{file_name}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("file_name") String fileName,
            HttpServletResponse response) {
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(new File(this.testOutputDirectory+"/"+fileName+"/MVICFG.dot"));
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @ResponseBody
    @GetMapping("mvicfg/summary/{version1}/{version2}")
    public Summary getSummary(@PathVariable String version1,@PathVariable String version2,HttpSession session, Model model) {
        Summary summary=new Summary();
        try {
            String inputDir= (String) session.getAttribute("inputDir");
            summary.setVersion1Name(version1);
            summary.setVersion2Name(version2);
            summary.setVersion1Loc(FileUtil.countLines(inputDir+'/'+version1));
            summary.setVersion2Loc(FileUtil.countLines(inputDir+'/'+version2));
            Integer[] cfgsize=FileUtil.getMVICFGSize(this.testOutputDirectory+"/"+version1+"_"+version2+"/MVICFG.dot");
            summary.setMvicfgNumNode(cfgsize[0]);
            summary.setMvicfgNumEdge(cfgsize[1]);
            summary.setMvicfgBuildTime(FileUtil.readBuildTime(this.testOutputDirectory+"/"+version1+"_"+version2+"/"+"buildtime.txt"));
            summary.setChurnRate(FileUtil.getChurnRate(
                    this.testOutputDirectory+"/"+version1+"_"+version2+"/"+"diff.diff"));
            Graph graph= FileUtil.getAddedGraph(
                    this.testOutputDirectory + "/" + version1 + "_" + version2 + "/output_file.txt");

            summary.setPathsAdded(FileUtil.getAddedPath(graph.getAdj(), graph.getNodes()));

            graph= FileUtil.getAddedGraph(
                    this.testOutputDirectory + "/" + version2 + "_" + version1 + "/output_file.txt");

            summary.setPathsRemoved(FileUtil.getAddedPath(graph.getAdj(), graph.getNodes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
    }

    @PostMapping("/analyze") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("version1") MultipartFile version1, HttpSession session,
                                   Model model) {

        if (version1.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        Map<String,String> resultOptions=new HashMap<>();


        try {
            FileUtil.deleteUploadDir(this.uploadDirectory);
            FileUtil.deleteUploadDir(this.testOutputDirectory);

            String[] command = {"sh", "-c", "cd /home/Hydrogen/MVICFG/;mkdir "+this.hydrogenTestDirectory+";"};
            dockerService.runCommand(command);
            FileUtil.createUploadDir(this.uploadDirectory);
            //FileUtil.createUploadDir(this.testOutputDirectory);

            String inputDir=FileUtil.saveFile(version1, this.uploadDirectory);
            FileUtil.extractZipFiles(inputDir, this.uploadDirectory);

            inputDir=inputDir.substring(0,inputDir.indexOf("."));
            session.setAttribute("inputDir", inputDir);

            Collection<File> files=FileUtil.getAllFiles(this.uploadDirectory, "c");

            List<String> version_file_names= new ArrayList<>();
            List<String> version_file_bc_names= new ArrayList<>();

            files.forEach((temp) -> {
                System.out.println(temp.getName());
                version_file_names.add(temp.getName());
            });

            dockerService.clearDirectory(this.hydrogenTestDirectory);

            dockerService.copyToDocker(inputDir, this.hydrogenTestDirectory);
            for(String fn: version_file_names)
            {
                String bcfn=fn.substring(0,fn.indexOf("."))+".bc";
                dockerService.compile(this.hydrogenTestDirectory,fn,bcfn);
                version_file_bc_names.add(bcfn);
            }
            for(int i=0; i<version_file_names.size();i++)
            {
                for(int j=0; j<version_file_names.size();j++)
                {
                    if (i==j)
                        continue;
                    String x1=version_file_names.get(i);
                    String x2=version_file_names.get(j);
                    String y1=version_file_bc_names.get(i);
                    String y2=version_file_bc_names.get(j);

                    long start = System.currentTimeMillis();

                    dockerService.runHydrogen(this.hydrogenTestDirectory, x1, x2,y1, y2);

                    long end = System.currentTimeMillis();
                    float sec = (end - start) / 1000F;

                    dockerService.copyFromDocker("BuildNinja",
                            this.testOutputDirectory+"/"+x1+"_"+x2);
                    FileUtil.createDiffFile(inputDir+"/"+x1, inputDir+"/"+x2,
                            this.testOutputDirectory+"/"+x1+"_"+x2);
                    resultOptions.put(x1+"_"+x2, x1+" - "+x2);

                    FileUtil.saveBuildTime(this.testOutputDirectory+"/"+x1+"_"+x2+"/buildtime.txt", sec);


                }
            }

//            FileUtil.deleteUploadDir(this.uploadDirectory);
//            FileUtil.deleteUploadDir(this.testOutputDirectory);


        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("resultOptions", resultOptions);
        model.addAttribute("message", "Files uploaded successfully");
        return "result";
    }
}
