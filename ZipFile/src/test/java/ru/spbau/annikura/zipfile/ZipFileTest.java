package ru.spbau.annikura.zipfile;

import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ZipFileTest {

    @Test
    public void simpleTextFile() throws Exception {
        String startPath = "src/test/resources/singleTextFileTest/file.zip";
        String resultPath = "src/test/resources/singleTextFileTest/result/file";
        File newFile = new File(resultPath);
        File zipFile = new File(startPath);
        try {
            ZipFile.unzipFilesIf(zipFile, zipFile.getParent() + File.separator + "result", s -> true);
            assertEquals(true, newFile.exists());
            List<String> contents = Files.readAllLines(Paths.get(resultPath), StandardCharsets.UTF_8);
            assertEquals(1, contents.size());
            assertEquals("This is a simple test.", contents.get(0));
        } finally {
            assertEquals(true, newFile.delete());
        }
    }

    @Test
    public void filtration() throws Exception {
        String startPath = "src/test/resources/filtrationTest/file.zip";
        String resultPath = "src/test/resources/filtrationTest/result/";
        File resultDir = new File(resultPath);
        File zipFile = new File(startPath);
        File[] files = {};
        try {
            ZipFile.unzipFilesIf(zipFile, zipFile.getParent() + File.separator + "result", s -> s.contains("f"));
            assertEquals(true, resultDir.exists());
            files = resultDir.listFiles();
            assertEquals(3, files.length);
            for (File file : files)
                assertEquals(true, file.getName().contains("f"));
        } finally {
            for (File file : files) {
                assertEquals(true, file.delete());
            }
        }
    }

    @Test
    public void folders() throws Exception {
        String startPath = "src/test/resources/foldersTest/file";
        String resultPath = "src/test/resources/foldersTest/result/";
        File resultDir = new File(resultPath);
        File zipFile = new File(startPath);
        try {
            ZipFile.unzipFilesIf(zipFile, zipFile.getParent() + File.separator + "result", s -> false);
            assertEquals(true, resultDir.exists());
            assertEquals(3, resultDir.list().length);
        } finally {
            for (File dir : resultDir.listFiles()) {
                assertEquals(true, dir.delete());
            }
        }
    }

    @Test(expected = FileSystemException.class)
    public void fileAlreadyExists() throws Exception {
        String startPath = "src/test/resources/fileAlreadyExistsTest/file";
        File zipFile = new File(startPath);
        ZipFile.unzipFilesIf(zipFile, zipFile.getParent() + File.separator + "result", s -> true);
    }
}