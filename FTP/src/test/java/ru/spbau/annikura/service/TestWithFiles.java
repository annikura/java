package ru.spbau.annikura.service;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class TestWithFiles {
    String mainDir;
    String fileWithText;
    byte[] fileContent = {42, 34};
    String subdir;
    String fileInSubdir;
    String emptyFile;

    @After
    public void eraseFiles() {
        for (String name : new String[]{emptyFile, fileWithText, fileInSubdir, subdir, mainDir}) {
            File file = new File(name);
            file.delete();
        }
    }

    @Before
    public void setUpFiles() throws Exception {
        File mainDir = new File(Paths.get("src", "main", "resources", "dir").toString());
        assert mainDir.exists() || mainDir.mkdirs();
        File emptyFile = new File(mainDir, "emptyFile");
        assert emptyFile.exists() || emptyFile.createNewFile();
        File fileWithText = new File(mainDir, "fileWithText");

        FileOutputStream fos = new FileOutputStream(fileWithText);
        fos.write(fileContent);

        File subdir = new File(mainDir, "subdir");
        assert subdir.exists() || subdir.mkdir();
        File fileInSubdir = new File(subdir, "file");
        assert fileInSubdir.exists() || fileInSubdir.createNewFile();

        this.mainDir = mainDir.getAbsolutePath();
        this.subdir = subdir.getAbsolutePath();
        this.fileWithText = fileWithText.getAbsolutePath();
        this.emptyFile = emptyFile.getAbsolutePath();
        this.fileInSubdir = fileInSubdir.getAbsolutePath();
    }
}
