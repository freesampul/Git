import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        assertTrue (f.exists());
    }

    @Test
    void testCreateTree() throws Exception {
        tree t = new tree();
        assertEquals(t.getSHA1(),a.createTree());
        File f = new File (t.getSHA1());
        // assertTrue(f.exists());
    }

    @Test
    void testGenerateSha1() {
        
    }
}
