import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.IOException;

public class index extends blob {

    public static HashMap<String, String> files = new HashMap<>();

    public static void init() throws IOException {
        String folder = "./objects";

        java.nio.file.Path folderPath = Paths.get(folder);

        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path index = folderPath.resolve("index");
        if (!Files.exists(index)) {
            try {
                Files.createFile(index);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
 }

    public static void commit(String fileName) throws IOException {
        try {
            String hashName = blobFile(fileName);
            files.put(fileName, hashName);

            Path index = Paths.get("index");
            try (BufferedWriter write = Files.newBufferedWriter(index, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
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
            Path index = Paths.get("./objects/index");
            try (BufferedWriter clear = Files.newBufferedWriter(index, StandardOpenOption.TRUNCATE_EXISTING)) {
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter write = Files.newBufferedWriter(index, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                for (Map.Entry<String, String> entry : files.entrySet()) {
                    write.write(entry.getKey() + " : " + entry.getValue());
                    write.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
