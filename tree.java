import java.util.*;
import java.io.*;

public class Tree {
    private StringBuilder sData;
    private Map<String, String> hMap;
    private List<String> tree;

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

    public void addIndex() throws IOException {
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
                }
            } else {
                sData.append(line).append("\n");
            }
        }
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
            if (!curLine.contains(sFile))
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

    public String addDirectory (String directoryPath) throws IOException
    {
        File directory = new File(directoryPath);
        if(!directory.exists() || !directory.isDirectory())
        {
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
}
