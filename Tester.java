import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

public class Tester {
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        File file = new File("input.txt");
        FileWriter fw = new FileWriter(file);
        fw.write("this is input to the file");
        fw.close();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Files.deleteIfExists(Paths.get("input.txt"));
        Files.deleteIfExists(Paths.get("index"));
        Files.deleteIfExists(Paths.get("./objects/"));
    }

    @Test
    @DisplayName("Testing init")
    void testInitialize() throws Exception {
        index.init();
        File objects = new File("./objects/");
        assertTrue(objects.exists() && objects.isDirectory());
        File file = new File("index");
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Testing add")
    void testCreateBlob() throws Exception {
        File file = new File(tempDir.toFile(), "index.txt");
        String sFill = "This is a test";
        Files.write(file.toPath(), sFill.getBytes());

        StringBuilder i = new StringBuilder();
        blob.write(blob.hashStringToSHA1(sFill), i);

        File blobFile = new File("./objects/" + blob.hashStringToSHA1(sFill));
        assertTrue(blobFile.exists());

        file.delete();
        blobFile.delete();
    }

    @Test
    @DisplayName("Testing remove blob")
    void testRemoveBlob() throws Exception {
        File file = new File(tempDir.toFile(), "test.txt");
        String sFill = "File contents";
        Files.write(file.toPath(), sFill.getBytes());

        index.init();
        index.commit(file.getAbsolutePath());
        index.remove(file.getAbsolutePath());

        String sContents = Index.fileToString("index");
        assertFalse(sContents.contains("test.txt"));

        File object = new File("./objects/" + blob.hashStringToSHA1(file.getAbsolutePath()));
        assertFalse(object.exists());
        file.delete();
    }

    @Test
    @DisplayName("Tests tree methods")
    public void testTree() throws Exception{
        tree tree = new tree();
        String str = "blob : " + blob.hashStringToSHA1("input") + " : input.txt";
        tree.add(str);
        tree.add("blob : " + blob.hashStringToSHA1("input2") + " : input2.txt");
        tree.remove("input2.txt");
        tree.writeToFile();
        assertTrue(Files.exists(Paths.get("./objects/" + blob.hashStringToSHA1(str))));
        BufferedReader br = new BufferedReader(new FileReader("./objects/" + blob.hashStringToSHA1));
        String strBR = br.readLine();
        br.close();
        assertEquals(str, strBR);
    }
}
