import java.io.*;

public class Main {
    private String filePath;

    public Main(String filePath) {
        this.filePath = filePath;
    }

    public void writeToDisk(byte[] data, long position) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek(position);
            file.write(data);
        }
    }

    public byte[] readFromDisk(long position, int length) throws IOException {
        byte[] data = new byte[length];
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(position);
            file.read(data);
        }
        return data;
    }


    public static void main(String[] args) {
        Main externalMemory = new Main("external_data.dat");

        byte[] dataToWrite = "Hello, External Memory!".getBytes();
        long position = 0;

        try {
            externalMemory.writeToDisk(dataToWrite, position);

            byte[] readData = externalMemory.readFromDisk(position, dataToWrite.length);
            String result = new String(readData);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
