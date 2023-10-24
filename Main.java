import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            Index.init();
            Index.add("input.txt");
            Index.add("second.txt");
            Tree t = new Tree();
            t.add("blob : 968fa8b4347b5bee8aea30a918f15ea98d9351ca");
            t.writeToObjects();
            Commit a = new Commit("", "Sam2",
                    "test2");
            Commit b = new Commit(a.getParentTree(), a.generateSha1(), "Sam2", "test2commit");
            System.out.println("test");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("test");
        // try {
        // Index.init();
        // Index.add("input.txt");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // System.out.println("this works so far!");
        // Tree t = new Tree();
        // t.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        // t.add("tree : bc323153dcce17da2a8cd62cb240abdc49f3fe7b");
        // System.out.println(t.getString());
        // try {
        // t.writeToObjects();
        // System.out.println("Removing now!");
        // t.remove("file1.txt");
        // System.out.println(t.getString());
        // t.remove("bc323153dcce17da2a8cd62cb240abdc49f3fe7b");
        // System.out.println(t.getString());
        // System.out.println("Line aboce should be blank?");
        // } catch (IOException eep) {
        // eep.printStackTrace();
        // }
        // System.out.println("tesst");
        // String file = "/Users/jparker/Desktop/Git/input.txt";
        // try {
        // Index.init();
        // Index.commit(file);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // Tree t = new Tree();
        // t.add(file);
        // try {
        // t.writeToFile();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
    }
}