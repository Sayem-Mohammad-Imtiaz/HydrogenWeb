package com.iastate.web.hydrogen.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DockerService {
    private DockerClient docker;
    @Value("${docker.image.id}")
    private String dockerImageID;
    @Value("${docker.id}")
    private String id;
    @Value("${hydrogen.path}")
    public String baseHydrogenPath;

    public DockerService() throws DockerCertificateException, DockerException, InterruptedException {
        docker = DefaultDockerClient.fromEnv().build();
    }
    public String runCommand(String[] command) throws DockerException, InterruptedException {
        final ExecCreation execCreation = docker.execCreate(
                this.id, command, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        LogStream output = docker.execStart(execCreation.id());
        String execOutput = output.readFully();
        return execOutput;
    }

    public void copyFromDocker(String path, String outputPath) throws DockerException, InterruptedException, IOException {
        path=this.baseHydrogenPath+path;
//        path="/home/Hydrogen/MVICFG/"+path;
//        this.id="d7eddea3fb00";

        try (final TarArchiveInputStream tarStream =
                     new TarArchiveInputStream(docker.archiveContainer(this.id, path))) {
            TarArchiveEntry entry;
            byte[] b = new byte[100000];

            while ((entry = tarStream.getNextTarEntry()) != null) {
                // Do stuff with the files in the stream
                if(entry.isFile())
                {
                    String filename=entry.getName();
                    filename=filename.substring(filename.lastIndexOf("/")+1,filename.length());
                    if(!filename.equals("MVICFG.dot") && !filename.equals("output_file.txt"))
                        continue;

                    filename=outputPath+"/"+filename;
                    final File file = new File(filename);

                    final File parent = file.getParentFile();
                    if (!parent.exists()) {
                        if (!parent.mkdirs()) {
                            throw new IOException("Unable to create folder " + parent.getAbsolutePath());
                        }
                    }

                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        int r;
                        while ((r = tarStream.read(b)) != -1) {
                            fos.write(b, 0, r);
                        }
                    }
                }
            }
        }
    }

    public void copyToDocker(String fromPath,String toPath ) throws InterruptedException, DockerException, IOException {
        System.out.println(new java.io.File(fromPath).toPath());
        toPath=this.baseHydrogenPath+"/"+toPath;
        System.out.println(toPath);

        docker.copyToContainer
                (new java.io.File(fromPath).toPath(), this.id, toPath);

    }
    public void compile(String path, String filename, String binaryFileName) throws DockerException, InterruptedException {

        path=this.baseHydrogenPath+path;

        String[] command = {"sh", "-c", "cd "+path+";" +
                " clang -c -O0 -Xclang -disable-O0-optnone -g -emit-llvm -S "+filename
                +" -o "+binaryFileName};
        this.runCommand(command);
    }

    public void buildHydrogen() throws DockerException, InterruptedException {
        String[] command = {"sh", "-c", "cd "+this.baseHydrogenPath+"; mkdir BuildNinja; cmake -B BuildNinja -G Ninja .;" +
                "cd BuildNinja; ninja"};
        this.runCommand(command);
    }
    public void runHydrogen(String path, String filenameV1, String filenameV2, String binaryFileNameV1,
                            String binaryFileNameV2)
            throws DockerException, InterruptedException {

        filenameV1="../"+path+"/"+filenameV1;
        filenameV2="../"+path+"/"+filenameV2;
        binaryFileNameV1="../"+path+"/"+binaryFileNameV1;
        binaryFileNameV2="../"+path+"/"+binaryFileNameV2;

        String[] command = {"sh", "-c", "cd "+this.baseHydrogenPath+"/BuildNinja; " +
                " ./Hydrogen.out "+binaryFileNameV1+" "+binaryFileNameV2+
                " :: "+filenameV1+" :: "+filenameV2};
        this.runCommand(command);
    }
    public void clearDirectory(String path)
            throws DockerException, InterruptedException {
        path=this.baseHydrogenPath+path;
        String[] command = {"sh", "-c", "cd "+path+"; rm -rf *;"};
        this.runCommand(command);
    }


//    public static void main(String [] args)
//    {
//        String[] command = {"sh", "-c", "cd /home/Hydrogen/MVICFG/;ls"};
//
//        try {
//            DockerService ds=new DockerService();
////            ds.compile(ds.baseHydrogenPath+"TestPrograms/Buggy","Prog.c","ProgV1.bc");
////            ds.compile(ds.baseHydrogenPath+"TestPrograms/Correct","Prog.c","ProgV2.bc");
////            //ds.buildHydrogen();
////           // System.out.println(ds.runCommand(command));
////
////            ds.runHydrogen("","TestPrograms/Buggy/Prog.c","TestPrograms/Correct/Prog.c",
////                    "TestPrograms/Buggy/ProgV1.bc",
////                    "TestPrograms/Correct/ProgV2.bc");
//////            ds.copyToDocker("lokkhi", "/hydrogen/TestPrograms/New/");
//            ds.copyFromDocker("BuildNinja","hydrogen_analysis/hydrogen_output/v1v2");
//        } catch (DockerCertificateException e) {
//            e.printStackTrace();
//        } catch (DockerException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
