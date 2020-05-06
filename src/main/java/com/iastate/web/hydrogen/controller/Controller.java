package com.iastate.web.hydrogen.controller;

import com.iastate.web.hydrogen.service.DockerService;
import com.iastate.web.hydrogen.util.FileUtil;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @PostMapping("/analyze") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("version1") MultipartFile version1,
                                   Model model) {

        if (version1.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            FileUtil.createUploadDir(this.uploadDirectory);
            //FileUtil.createUploadDir(this.testOutputDirectory);

            String inputDir=FileUtil.saveFile(version1, this.uploadDirectory);
            FileUtil.extractZipFiles(inputDir, this.uploadDirectory);

            inputDir=inputDir.substring(0,inputDir.indexOf("."));

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
                    if(i==j)
                        continue;

                    dockerService.runHydrogen(this.hydrogenTestDirectory,
                            version_file_names.get(i),
                            version_file_names.get(j),
                            version_file_bc_names.get(i),
                            version_file_bc_names.get(j));

                    dockerService.copyFromDocker("BuildNinja", this.testOutputDirectory+"/"+"v"+(i+1)+"_v"+(j+1));
                }
            }

            FileUtil.deleteUploadDir(this.uploadDirectory);
//            FileUtil.deleteUploadDir(this.testOutputDirectory);


        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Files uploaded successfully");
        return "index";
    }
}
