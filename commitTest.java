import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class commitTest {
    static Commit a;
    static Commit b;
    static Commit c;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        Commit c = new Commit("", "", "Mark Ma", "this is a test");
        Commit a = new Commit("", c.generateSha1(), "William", "test2");
        Commit b = new Commit("", a.generateSha1(), "Sammy", "test3");

        File file3 = new File("file3");
    }

    @Test
    void testGetDate() {
        // it works
    }

    @Test
    void testWriteOut() throws IOException {
        File f = new File(c.generateSha1());
        assertEquals(f.exists(), true);
    }

    @Test
    void testCreateTree() throws Exception {
        Tree t = new Tree();
        assertEquals(t.getSHA1(), a.createTree());
        File f = new File(t.getSHA1());
        assertEquals(f.exists(), true);
    }

    @Test
    void testGenerateSha1() throws IOException {
        b.writeOut();
        File f = new File(b.generateSha1());
        assertEquals(f.exists(), true);
    }

    @Test
    void test1commit() throws Exception {
        File file1 = new File("file1");
        File file2 = new File("file2");
        Commit onecommit = new Commit("adkjahsdkjaskjhad", "", "Mark Ma", "this is a test");
        assertEquals("adkjahsdkjaskjhad", onecommit.getParentTree());
        assertEquals("", onecommit.getParentCommit());
        assertEquals("Mark Ma", onecommit.getAuthor());
        assertEquals("this is a test", onecommit.getSummary());
        assertEquals("", onecommit.getNextSha());

        assertNotNull(onecommit.getParentTree());
        // assertTrue(onecommit.getParentTree().length() > 0);
    }

    @Test
    void test2commits() throws Exception {
        File file1 = new File("file1");
        File file2 = new File("file2");
        mkdir 
        Commit onecommit = new Commit("adkjahsdkjaskjhad", "", "Mark Ma", "this is a test");
        assertEquals("adkjahsdkjaskjhad", onecommit.getParentTree());
        assertEquals("", onecommit.getParentCommit());
        assertEquals("Mark Ma", onecommit.getAuthor());
        assertEquals("this is a test", onecommit.getSummary());
        assertEquals("", onecommit.getNextSha());

        assertNotNull(onecommit.getParentTree());
        //assertTrue(onecommit.getParentTree().length() > 0);
    }
}
