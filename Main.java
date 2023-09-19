import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String file = "/Users/jparker/Desktop/Git/input.txt";
        try {
            index.init();
            index.commit(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tree t = new tree();
        t.add(file);
        try {
            t.writeToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
