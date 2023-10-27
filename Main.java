import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            Index.init();
            // Index.addFile("input.txt");
            // Index.addFile("second.txt");
            Tree t = new Tree();
            // t.add("blob : 968fa8b4347b5bee8aea30a918f15ea98d9351ca");
            t.writeToObjects();
            Commit a = new Commit("d2c9ab373b1069f4d6a4709f42807d1fe96580a2",
                    "a197c61105f4d9de7c97803fbd11ea091c313a20", "Sam", "testing23");
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