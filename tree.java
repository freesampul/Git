import java.util.*;

import javafx.scene.Parent;

import java.io.*;

public class Tree {
    private StringBuilder sData;
    private Map<String, String> hMap;
    private List<String> tree;

    private static final String deleted = "*deleted*";
    private static final String edited = "*edited*";

    public Tree() {
        sData = new StringBuilder();
        hMap = new HashMap<>();
        tree = new ArrayList<>();
    }

    public void writeToFile() throws Exception {
        String sFile = Blob.hashStringToSHA1(sData.toString());
        PrintWriter w_git = new PrintWriter(new BufferedWriter(new FileWriter("./objects/" + sFile)));
        w_git.print(sData.toString());
        w_git.close();
    }

    public void addIndex(String parentTree) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("index"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length >= 3) {
                    String type = parts[0];
                    String hash = parts[1];
                    String name = parts[2];
                    if (type.equals("blob")) {
                        sData.append("blob").append(hash).append(" : ").append(name).append("\n");
                    } else if (type.equals("tree")) {
                        sData.append("tree : ").append(hash).append("\n");
                    } else {
                        deleteOrEdit(line, parentTree);
                    }
                } else {
                    sData.append(line).append("\n");
                }
            }
        }
    }

    public void replaceEditedFileEntry(String fileName, String newSha) {
        String[] lines = sData.toString().split("\\n");
        StringBuilder newContent = new StringBuilder();
        for (String line : lines) {
            if (line.contains(fileName)) {
                line = "blob : " + newSha + " : " + fileName;
            }
            newContent.append(line).append("\n");
        }

        sData = new StringBuilder(newContent.toString().trim());
    }

    public void markEdited(String sFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("index", true))) {
            bw.write(edited + " " + sFile + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String sLine) {
        String strData = sData.toString();
        if (strData.contains(sLine))
            return;
        else {
            sData.append(sLine + "\n");
        }
    }

    public void remove(String sFile) {
        String sContent = sData.toString();
        if (!sContent.contains(sFile))
            return;
        StringBuilder newContent = new StringBuilder();
        Scanner sc = new Scanner(sContent);
        while (sc.hasNextLine()) {
            String curLine = sc.nextLine();
            if (curLine.contains(sFile))
                newContent.append("\n");
            else
                newContent.append(curLine).append("\n");
        }
        sc.close();
        sData = newContent;
    }

    public void getContents() throws IOException {
        for (Map.Entry<String, String> k : hMap.entrySet()) {
            String line = "blob : " + k.getKey() + " : " + k.getValue();
            sData.append(line).append("\n");
        }
        for (int i = 0; i < tree.size(); i++) {
            String line = "tree : " + tree.get(i);
            sData.append(line).append("\n");
        }
        File fileToDelete = new File("tree");
        fileToDelete.delete();
        sData = new StringBuilder(sData.toString().trim());
    }

    public String getString() {
        return sData.toString().trim();
    }

    public String getSHA1() {
        return Blob.hashStringToSHA1(sData.toString());
    }

    public void writeToObjects() throws IOException {
        File shaName = new File("objects/" + Blob.hashStringToSHA1(sData.toString()));
        if (!shaName.exists()) {
            shaName.createNewFile();
        }
        if (shaName.exists()) {
            FileWriter fw = new FileWriter(shaName);
            sData.toString().trim();
            fw.write(sData.toString());
            fw.close();
        }
    }

    public String addDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Blud this isn't a directory bud");
        }
        File[] files = directory.listFiles();
        Tree t = new Tree();

        for (File file : files) {
            if (file.isDirectory()) {
                Tree another = new Tree();
                t.add("tree : " + another.getSHA1());
            } else {
                Blob b = new Blob(file.getAbsolutePath());
                t.add("blob : " + b.getSha() + " : " + file.getName());
                hMap.put(b.getSha(), file.getName());
            }
        }
        t.writeToObjects();
        return t.getSHA1();
    }

    private String readFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public void deleteOrEdit(String content, String parentCommit) throws IOException {
        if (content.contains(deleted)) {
            findFile(content.substring(10), Commit.getTreeFromSha(parentCommit));
        } else if (content.contains(edited)) {
            String orginalFile = content.substring(9);
            findFile(orginalFile, Commit.getTreeFromSha(parentCommit));
            Scanner scan = new Scanner(new File(content.substring(9)));
            String edit = scan.useDelimiter("\\A").next();
            scan.close();
            String newSha = Blob.hashStringToSHA1(edit);
            add("blob : " + newSha + " : " + orginalFile);
        }
    }

    public void findFile(String sha, String parentSha) throws IOException {
        File file = new File("./objects/" + parentSha);
        if (file.length() > 0) {
            Scanner scan = new Scanner(file);
            String content = scan.useDelimiter("\\A").next();
            scan.close();
            if (content.contains(sha)) {
                FileInputStream fileInput = new FileInputStream("./objects" + parentSha);
                DataInputStream data = new DataInputStream(fileInput);
                BufferedReader br = new BufferedReader(new InputStreamReader(data));
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.contains(sha))
                        add(line);
                }
                data.close();
            } else {
                FileInputStream fileInput = new FileInputStream("./objects" + parentSha);
                DataInputStream data = new DataInputStream(fileInput);
                BufferedReader br = new BufferedReader(new InputStreamReader(data));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.substring(6).indexOf(":") == -1 && line.contains("tree"))
                        findFile(sha, line.substring(7, 47));
                    else
                        add(line);
                }
                br.close();
            }
        }
    }
}
