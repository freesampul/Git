import java.util.*;
import java.io.*;

public class tree {
    private StringBuilder sData;
    private Map<String, String> hMap;
    private List<String> tree;

    public tree() {
        sData = new StringBuilder();
        hMap = new HashMap<>();
        tree = new ArrayList<>();
    }

    public void writeToFile() throws Exception {
        String sFile = blob.hashStringToSHA1(sData.toString());
        PrintWriter w_git = new PrintWriter(new BufferedWriter(new FileWriter("./objects/" + sFile)));
        w_git.print(sData.toString());
        w_git.close();
    }

    public void addIndex() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("index"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" : ");
                String type = parts[0];
                String hash = parts[1];
                String name = parts[2];
                if (type.equals("blob")) {
                    sData.append("blob : ").append(hash).append(" : ").append(name).append("\n");
                } else if (type.equals("tree")) {
                    sData.append("tree : ").append(hash).append("\n");
                }
            }
        }
    }

    public void add(String sLine) {
        String strData = sData.toString();
        if (strData.contains(sLine))
            return;
        if (sData.isEmpty())
            sData.append(sLine);
        else {
            sData.append("\n");
            sData.append(sLine);
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
        return sData.toString();
    }

    public String getSHA1() {
        return blob.hashStringToSHA1(sData.toString());
    }

    public void writeToObjects() throws IOException {
        File shaName = new File("objects/" + blob.hashStringToSHA1(sData.toString()));
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
}
