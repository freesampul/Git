import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class finalTester {
    static Commit a;
    static Commit b;
    static Commit c;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        File file1 = new File("file1");
        File file2 = new File("file2");
        File file3 = new File("file3");
    }

    @Test
    void test1commit() throws Exception {
        Index.init();
        Index.add("file1");
        Index.add("file2");
        Commit onecommit = new Commit("", "", "Sam", "testing 1 commit");
        assertEquals("", onecommit.getParentTree());
        assertEquals("", onecommit.getParentCommit());
        assertEquals("Sam", onecommit.getAuthor());
        assertEquals("testing 1 commit", onecommit.getSummary());
        assertEquals("", onecommit.getNextSha());

        assertNotNull(onecommit.getParentTree());
        // assertTrue(onecommit.getParentTree().length() > 0);
    }

    @Test
    void test2commits() throws Exception {
       
    }
}
