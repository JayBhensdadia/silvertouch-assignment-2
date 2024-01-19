import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


interface FileOperation {
    void execute() throws IOException;

    void undo() throws IOException;
}


class CreateFileOperation implements FileOperation {
    private final String filePath;

    CreateFileOperation(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() throws IOException {
        FileWriter writer = new FileWriter(filePath);
        
        writer.close();
    }

    @Override
    public void undo() throws IOException {
        java.io.File file = new java.io.File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}


class Transaction {
    private final List<FileOperation> operations = new ArrayList<>();

    public void addOperation(FileOperation operation) {
        operations.add(operation);
    }

    public void commit() throws IOException {
        for (FileOperation operation : operations) {
            operation.execute();
        }
        operations.clear(); 
    }

    public void rollback() throws IOException {
        for (int i = operations.size() - 1; i >= 0; i--) {
            operations.get(i).undo();
        }
        operations.clear(); 
    }
}

public class Main {
    public static void main(String[] args) {
        
        Transaction transaction = new Transaction();

        
        transaction.addOperation(new CreateFileOperation("file1.txt"));
        transaction.addOperation(new CreateFileOperation("file2.txt"));

        try {
            
            transaction.commit();
            System.out.println("Transaction committed successfully.");
        } catch (IOException e) {
            
            System.err.println("Transaction commit failed: " + e.getMessage());

            try {
                
                transaction.rollback();
                System.out.println("Transaction rolled back.");
            } catch (IOException rollbackException) {
                
                System.err.println("Transaction rollback failed: " + rollbackException.getMessage());
            }
        }
    }
}
