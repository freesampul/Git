import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class commit {
    static tree t;
    private String summary;
    private String author;
    private String parentTree;
    private String parentCommit;

    // This is the initial commit, sorry jake wrote this weird to me
    public commit(String parentTree, String parentCommit, String author, String summary) throws Exception// if no parent
                                                                                                         // Sha1 just
                                                                                                         // enter ""
    {
        if (parentTree == "") {
            this.parentTree = createTree();
        } else {
            this.parentTree = parentTree;
        }
        this.summary = summary;
        this.author = author;
        this.parentCommit = parentCommit;
        clearIndex();
    }

    // Clears the inex
    private void clearIndex() throws IOException {
        FileWriter fw = new FileWriter(new File("index"));
        fw.write("");
        fw.close();
    }

    // Generates date for the commit
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return (dateFormat.format(date));
    }

    // Creates the tree if it doesn't exist
    public String createTree() throws Exception {
        tree t = new tree();
        t.addIndex();
        return (t.getSHA1());
    }

    // Generates a sha. Oh jakey, we have this somewhere else in our code jake why
    // can't we reuse it jake why rewrite it jake
    public String generateSha1() throws IOException {
        File f = new File("temp.txt");
        f.createNewFile();
        PrintWriter pw = new PrintWriter(f);
        pw.println(parentTree);
        pw.println(parentCommit);
        pw.println();
        pw.println(author);
        pw.println(getDate());
        pw.print(summary);
        pw.close();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("temp.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        f.delete();
        return (blob.hashStringToSHA1(sb.toString()));
    }

    // What was the point of this jake
    public void writeOut() throws IOException {
        File f = new File(generateSha1());
        f.createNewFile();
        PrintWriter pw = new PrintWriter(f);
        pw.println(parentTree);
        pw.println(parentCommit);
        File a = new File(parentCommit);
        if (a.exists()) {
            int lineNumber = 3;
            BufferedReader reader = new BufferedReader(new FileReader(parentCommit));
            StringBuilder content = new StringBuilder();
            String line;

            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNumber) {
                    content.append(generateSha1()).append("\n");
                }
                content.append(line).append("\n");
                currentLine++;
            }
            reader.close();

            FileWriter writer = new FileWriter(parentCommit);
            writer.write(content.toString());
            writer.close();
            //
            File input_file = new File(parentCommit);
            BufferedReader file_reader = new BufferedReader(new FileReader(parentCommit));

            StringBuilder file_content = new StringBuilder();
            String line1;
            int line_number = 0;

            while ((line1 = file_reader.readLine()) != null) {
                line_number++;
                if (line_number != 4) {
                    file_content.append(line1).append("\n");
                }
            }
            file_reader.close();
            FileWriter file_writer = new FileWriter(parentCommit);
            file_writer.write(file_content.toString());
            file_writer.close();
        }
        pw.println();
        pw.println(author);
        pw.println(getDate());
        pw.print(summary);
        pw.close();
    }

    public static void main(String[] args) throws Exception {
        commit c = new commit("adkjahsdkjaskjhad", "", "Mark Ma", "this is a test");
        commit a = new commit("", c.generateSha1(), "William", "test2");
        commit b = new commit("", a.generateSha1(), "Sammy", "test3");
        c.writeOut();
        a.writeOut();
        b.writeOut();
    }

    // Gets the tree associated with a hash
    public static String getTreeFromSha(String sha) throws IOException {
        String treeString = "";
        try (BufferedReader br = new BufferedReader(new FileReader("objects/" + sha))) {
            treeString = br.readLine();
            br.close();
        }
        return treeString;
    }

}