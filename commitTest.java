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
    static commit a;
    static commit b;
    static commit c;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        commit c = new commit("","","Mark Ma", "this is a test");
        commit a = new commit ("",c.generateSha1(),"William", "test2");
        commit b = new commit ("",a.generateSha1(),"Sammy", "test3");
    }
    @Test
    void testGetDate() {
        //it works
    }

    @Test
    void testWriteOut() throws IOException {
        File f = new File (c.generateSha1());
        assertEquals (f.exists(),true);
    }

    @Test
    void testCreateTree() throws Exception {
        tree t = new tree();
        assertEquals(t.getSHA1(),a.createTree());
        File f = new File (t.getSHA1());
        assertEquals(f.exists(),true);
    }

    @Test
    void testGenerateSha1() throws IOException {
        b.writeOut();
        File f = new File (b.generateSha1());
        assertEquals(f.exists(),true);
    }

    @Test
    void test1commit() throws Exception {
        commit onecommit = new commit("adkjahsdkjaskjhad", "", "Mark Ma", "this is a test");
        assertEquals("adkjahsdkjaskjhad", onecommit.getParentTree());
        assertEquals("", onecommit.getParentCommit());
        assertEquals("Mark Ma", onecommit.getAuthor());
        assertEquals("this is a test", onecommit.getSummary());
        assertEquals("", onecommit.getNextSha());

        assertNotNull(onecommit.getParentTree());
        assertTrue(onecommit.getParentTree().length() > 0);
    }
}
