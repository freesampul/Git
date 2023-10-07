import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

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
        folder("commitFoler");
        file("commitFoler/1", "1");
        file("commitFoler/2", "2");
        Index.add("commitFoler");
        Commit commit = new Commit("aaa", "", "kevin", "kill me");
        commit.writeOut();

        folder("commitFoler2");
        file("commitFoler2/1", "1");
        file("commitFoler2/2", "2");
        Index.add("commitFoler2");
        Commit commit2 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        try (FileReader read = new FileReader("objects/" + commit.generateSha1());
                BufferedReader br = new BufferedReader(read)) {
            String treeSha = br.readLine();
            String nextSha = br.readLine();
            assertEquals("aaa", treeSha);
            // assertEquals(commit2.generateSha1(), nextSha);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader read = new FileReader("objects/" + commit2.generateSha1());
                BufferedReader br2 = new BufferedReader(read)) {
            String treeSha2 = br2.readLine();
            String prevSha2 = br2.readLine();
            assertEquals("bbb", treeSha2);
            assertEquals(commit2.getParentCommit(), prevSha2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBillionsOfCommits() throws Exception {
        folder("commitFoler");
        file("commitFoler/1", "1");
        file("commitFoler/2", "2");
        Index.add("commitFoler");
        Commit commit = new Commit("aaa", "", "kevin", "kill me");
        commit.writeOut();

        folder("commitFoler2");
        file("commitFoler2/1", "1");
        file("commitFoler2/2", "2");
        Index.add("commitFoler2");
        Commit commit2 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        folder("commitFoler3");
        file("commitFoler3/1", "1");
        file("commitFoler3/2", "2");
        Index.add("commitFoler3");
        Commit commit3 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        folder("commitFoler4");
        file("commitFoler4/1", "1");
        file("commitFoler4/2", "2");
        Index.add("commitFoler3");
        Commit commit4 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        try (FileReader read = new FileReader("objects/" + commit.generateSha1());
                BufferedReader br = new BufferedReader(read)) {
            String treeSha = br.readLine();
            String nextSha = br.readLine();
            assertEquals("aaa", treeSha);
            // assertEquals(commit2.generateSha1(), nextSha);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader read = new FileReader("objects/" + commit2.generateSha1());
                BufferedReader br2 = new BufferedReader(read)) {
            String treeSha2 = br2.readLine();
            String prevSha2 = br2.readLine();
            assertEquals("bbb", treeSha2);
            assertEquals(commit2.getParentCommit(), prevSha2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileReader read = new FileReader("objects/" + commit3.generateSha1());
                BufferedReader br2 = new BufferedReader(read)) {
            String treeSha2 = br2.readLine();
            String prevSha2 = br2.readLine();
            assertEquals("bbb", treeSha2);
            assertEquals(commit2.getParentCommit(), prevSha2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileReader read = new FileReader("objects/" + commit4.generateSha1());
                BufferedReader br2 = new BufferedReader(read)) {
            String treeSha2 = br2.readLine();
            String prevSha2 = br2.readLine();
            assertEquals("bbb", treeSha2);
            assertEquals(commit2.getParentCommit(), prevSha2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void LastTestBRUHH() throws Exception {
        folder("commitFoler");
        file("commitFoler/1", "1");
        file("commitFoler/2", "2");
        Index.add("commitFoler");
        Commit commit = new Commit("aaa", "", "kevin", "kill me");
        commit.writeOut();

        folder("commitFoler2");
        file("commitFoler2/1", "1");
        file("commitFoler2/2", "2");
        Index.add("commitFoler2");
        Commit commit2 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        folder("commitFoler3");
        file("commitFoler3/1", "1");
        file("commitFoler3/2", "2");
        Index.add("commitFoler3");
        Commit commit3 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        folder("commitFoler4");
        file("commitFoler4/1", "1");
        Index.add("commitFoler4");
        Commit commit4 = new Commit("bbb", commit.generateSha1(), "kevin", "kill me");
        commit2.writeOut();

        file("commitFoler4/3", "3");
        Commit commit5 = new Commit("bruh", commit.generateSha1(), "Anrew Tiss", ":(");

        Commit[] commits = new Commit[] { commit, commit2, commit3, commit4, commit5 };
        for (Commit currentCommmit : commits) {
            System.out.println("Next commmit " + currentCommmit.getNextSha());
        }
    }

}
