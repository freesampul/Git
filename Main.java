import java.io.IOException;

public class Main {
    public static void main(String[] args) {

       try{
        Commit a = new Commit("", "", "Mark Ma", "this is a test, does not have parent because its first");
        Commit b = new Commit("", a.generateSha1(), "William", "test2 does not have next because most recent");
        System.out.println();
        a.writeOut();
        b.writeOut();
       } catch (IOException e) {
           e.printStackTrace();
       }

        // System.out.println("test");
        // try {
        //     Index.init();
        //     Index.add("input.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // System.out.println("this works so far!");
        // Tree t = new Tree();
        // t.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        // t.add("tree : bc323153dcce17da2a8cd62cb240abdc49f3fe7b");
        // System.out.println(t.getString());
        // try {
        //     t.writeToObjects();
        //     System.out.println("Removing now!");
        //     t.remove("file1.txt");
        //     System.out.println(t.getString());
        //     t.remove("bc323153dcce17da2a8cd62cb240abdc49f3fe7b");
        //     System.out.println(t.getString());
        //     System.out.println("Line aboce should be blank?");
        // } catch (IOException eep) {
        //     eep.printStackTrace();
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