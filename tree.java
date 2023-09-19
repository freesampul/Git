import java.util.*;
import java.io.*;

public class tree {
    private StringBuilder sData;
    public tree() {
        sData = new StringBuilder();
    }

    public void writeToFile() throws Exception {
        String sFile = blob.hashStringToSHA1(sData.toString());
        PrintWriter w_git = new PrintWriter(new BufferedWriter(new FileWriter("./objects/" + sFile)));
        w_git.print(sData.toString());
        w_git.close();
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
                newContent.append(curLine);
        }
        sc.close();
        sData = newContent;
    }
}
