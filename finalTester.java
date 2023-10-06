import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.stage.DirectoryChooser;

public class finalTester {
    static Commit a;
    static Commit b;
    static Commit c;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        Index.init();
    }

    public void file(String path, String content) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write(content);
        fw.close();
    }

    public void folder(String name) {
        File folder = new File(name);
        folder.mkdir();
    }

    @Test
    void test1commit() throws Exception {
        Index.init();
        Index.add("file1");
        Index.add("file2");
        Commit onecommit = new Commit("aaa", "", "Sam", "testing 1 commit");
        assertEquals("aaa", onecommit.getParentTree());
        assertEquals("", onecommit.getParentCommit());
        assertEquals("Sam", onecommit.getAuthor());
        assertEquals("testing 1 commit", onecommit.getSummary());
        assertEquals("", onecommit.getNextSha());
        // assertTrue(onecommit.getParentTree().length() > 0);
    }

    @Test
    void test2commits() throws Exception {
        File testerfolder = new File("TestFolder1");
        testerfolder.mkdir();
    }
}
