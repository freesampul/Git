import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Index {

    public static HashMap<String, String> files = new HashMap<>();

    public static void init() throws IOException {
        String folder = "./objects";
        Path folderPath = Paths.get(folder);
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path index = Paths.get("index");
        if (!Files.exists(index)) {
            try {
                Files.createFile(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void add(Tree tree) throws IOException {
        tree.getContents();
        String treeContents = tree.getString();
        String treeSha1 = Blob.hashStringToSHA1(treeContents);
        if (new File("objects/" + treeSha1).exists()) {
            System.out.println("added tree with SHA-1: " + treeSha1);
        }
        FileWriter fw = new FileWriter("index", true);
        fw.write("tree : " + treeSha1 + "\n");
        fw.close();
        tree.writeToObjects();

    }

    public static void add(String fileName) throws IOException {
        try {
            Blob b = new Blob(fileName);
            String hashName = b.getSha();
            files.put(fileName, hashName);

            Path index = Paths.get("index");
            try (BufferedWriter write = Files.newBufferedWriter(index, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE)) {
                for (Map.Entry<String, String> entry : files.entrySet()) {
                    write.write(entry.getKey() + " : " + entry.getValue());
                    write.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void remove(String fileName) throws IOException {
        try {
            files.remove(fileName);
            Path index = Paths.get("index");
            try (BufferedWriter clear = Files.newBufferedWriter(index, StandardOpenOption.TRUNCATE_EXISTING)) {
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter write = Files.newBufferedWriter(index, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE)) {
                for (Map.Entry<String, String> entry : files.entrySet()) {
                    write.write(entry.getKey() + " : " + entry.getValue());
                    write.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateIndexTree() throws IOException {
        File indexFile = new File("index");
        File temp = new File("tempind");

        BufferedReader br = new BufferedReader(new FileReader(indexFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String curr;
        while ((curr = br.readLine()) != null) {
            {
                if (curr.startsWith("blob")) {
                    String[] line = curr.split(" : ");
                    String orginalFile = line[0].substring(5).trim();
                    String sha = line[1].trim();
                    String updated = "blob : " + sha + " : " + orginalFile;
                    bw.write(updated);

                } else if (curr.startsWith("tree"))
                    bw.write(curr);
                else if (curr.startsWith("*deleted*") || curr.startsWith("*edited*")) {
                    bw.write(curr);
                }
            }
        }
        bw.close();
        bw.close();
        temp.renameTo(indexFile);
    }
}
