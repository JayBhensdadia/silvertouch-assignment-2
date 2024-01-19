import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

class TransactionalFileSystem {
    private Map<String, String> fileContents;
    private Map<String, Map<String, String>> indexing;
    private boolean inTransaction;

    public TransactionalFileSystem() {
        this.fileContents = new HashMap<>();
        this.indexing = new HashMap<>();
        this.inTransaction = false;
    }

    public void beginTransaction() {
        if (!inTransaction) {
            inTransaction = true;
        } else {
            throw new IllegalStateException("Transaction already in progress.");
        }
    }

    public void commit() {
        if (inTransaction) {
            inTransaction = false;
        } else {
            throw new IllegalStateException("No transaction in progress.");
        }
    }

    public void rollback() {
        if (inTransaction) {
            indexing.clear();
            inTransaction = false;
        } else {
            throw new IllegalStateException("No transaction in progress.");
        }
    }

    public void writeFile(String fileName, String content) {
        if (inTransaction) {

            fileContents.put(fileName, content);
            updateIndex(fileName, "content", content);
        } else {
            throw new IllegalStateException("Transaction not in progress.");
        }
    }

    public String readFile(String fileName) {
        return fileContents.get(fileName);
    }

    private void updateIndex(String fileName, String attribute, String value) {
        indexing.computeIfAbsent(fileName, k -> new HashMap<>()).put(attribute, value);
    }

    public void queryFiles(String attribute, String value) {
        if (inTransaction) {
            Map<String, String> result = new HashMap<>();

            indexing.forEach((fileName, attributes) -> {
                if (attributes.containsKey(attribute) && attributes.get(attribute).equals(value)) {
                    result.put(fileName, fileContents.get(fileName));
                }
            });

            System.out.println("Query Result: " + result);
        } else {
            throw new IllegalStateException("Transaction not in progress.");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        TransactionalFileSystem fileSystem = new TransactionalFileSystem();

        fileSystem.beginTransaction();

        fileSystem.writeFile("file1.txt", "Content for file 1");
        fileSystem.writeFile("file2.txt", "Content for file 2");

        fileSystem.commit();

        String content1 = fileSystem.readFile("file1.txt");
        String content2 = fileSystem.readFile("file2.txt");
        System.out.println("File 1 Content: " + content1);
        System.out.println("File 2 Content: " + content2);

        fileSystem.beginTransaction();

        fileSystem.writeFile("file3.txt", "Content for file 3");
        fileSystem.writeFile("file4.txt", "Content for file 4");

        fileSystem.queryFiles("attribute1", "value1");

        fileSystem.rollback();

        content1 = fileSystem.readFile("file3.txt");
        content2 = fileSystem.readFile("file4.txt");
        System.out.println("File 3 Content: " + content1);
        System.out.println("File 4 Content: " + content2);
    }
}
