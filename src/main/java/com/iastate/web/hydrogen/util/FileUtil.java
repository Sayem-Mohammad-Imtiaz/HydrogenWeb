package com.iastate.web.hydrogen.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {

    public static String createDiffFile(String file1, String file2, String outpath) throws IOException {
//       System.out.println("Diff: "+file1+" "+file2);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sh", "-c", "diff -U 0 "+file1+" "+file2+" > "+outpath+"/diff.diff");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String l = null;
        while ((l = reader.readLine()) != null) {
//               System.out.println(l);
        }
        return outpath+"/diff.diff";
    }

    public static Integer[] getMVICFGSize(String path) throws IOException {
        Integer edgeCount=0;
        String line;
        Set<Integer> nodeCount=new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                line=line.trim();
                if(line.matches("^\"[0-9]+\"\\s+->\\s+\"[0-9]+\".*"))
                {
                    String[] splited = line.split("\\s+");
                    nodeCount.add(Integer.parseInt(splited[0].replace("\"","")));
                    edgeCount++;
                }
            }
        }
        return new Integer[]{nodeCount.size(), edgeCount};
    }

    public static Integer getChurnRate(String path) throws IOException {
        int count=0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                if(line.startsWith("@@"))
                {
                    count++;
                }
            }
        }
        return count;
    }
    public static Integer countLines(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    public static void saveBuildTime(String path, float second) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            writer.write(String.valueOf(second));
        }
    }
    public static Float readBuildTime(String path) throws IOException {
        FileInputStream fisTargetFile = new FileInputStream(new File(path));

        String s= IOUtils.toString(fisTargetFile, "UTF-8");
        s=s.trim();
        return Float.parseFloat(s);
    }
    public static String readFile(String path) throws IOException {
        FileInputStream fisTargetFile = new FileInputStream(new File(path));

        return IOUtils.toString(fisTargetFile, "UTF-8");
    }
    public static void createUploadDir(String uploadDir) throws IOException {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    public static void deleteUploadDir(String uploadDir) throws IOException {
        FileUtils.deleteDirectory(new File(uploadDir));
    }
    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir +'/'+ file.getOriginalFilename());
        Files.write(path, bytes);
        return uploadDir +'/'+ file.getOriginalFilename();
    }
    public static void extractZipFiles(String file, String uploadDir) throws IOException {

        try (java.util.zip.ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(uploadDir,  entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(entryDestination)) {
                        IOUtils.copy(in, out);
                    }
                }
            }
        }
    }
    public static Collection<File> getAllFiles(String directory,String ext)
    {
        String regex="[a-zA-Z_].+[.]"+ext+"$";
        Collection<File> f= FileUtils.listFiles(new File(directory),
                new RegexFileFilter(regex), DirectoryFileFilter.DIRECTORY);
        return f;
    }
}
