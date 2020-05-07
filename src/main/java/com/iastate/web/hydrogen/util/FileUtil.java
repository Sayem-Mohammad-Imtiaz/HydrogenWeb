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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
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
