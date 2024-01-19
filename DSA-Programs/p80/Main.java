import java.io.*;
import java.util.*;

public class Main {

    public static void burstsort(String inputFile, String outputFile, int bufferSize) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String> buffer = new ArrayList<>();
            int fileNumber = 0;

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    if (!buffer.isEmpty()) {
                        Collections.sort(buffer);
                        writeToFile(buffer, outputFile, fileNumber++);
                    }
                    break;
                }

                buffer.add(line);

                if (buffer.size() == bufferSize) {
                    Collections.sort(buffer);
                    writeToFile(buffer, outputFile, fileNumber++);
                    buffer.clear();
                }
            }
        }
    }

    private static void writeToFile(List<String> data, String outputFile, int fileNumber) throws IOException {
        String fileName = String.format("%s_%d", outputFile, fileNumber);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public static void mergeFiles(String outputFile, String finalOutputFile) throws IOException {
        List<BufferedReader> readers = new ArrayList<>();

        for (int i = 0; ; i++) {
            String fileName = String.format("%s_%d", outputFile, i);
            File file = new File(fileName);

            if (!file.exists()) {
                break;
            }

            readers.add(new BufferedReader(new FileReader(file)));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(finalOutputFile))) {
            PriorityQueue<QueueEntry> minHeap = new PriorityQueue<>(Comparator.comparing(QueueEntry::getLine));

            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    minHeap.add(new QueueEntry(line, reader));
                }
            }

            while (!minHeap.isEmpty()) {
                QueueEntry entry = minHeap.poll();
                bw.write(entry.getLine());
                bw.newLine();

                String nextLine = entry.getReader().readLine();
                if (nextLine != null) {
                    minHeap.add(new QueueEntry(nextLine, entry.getReader()));
                }
            }
        }

        for (BufferedReader reader : readers) {
            reader.close();
        }
    }

    private static class QueueEntry {
        private final String line;
        private final BufferedReader reader;

        public QueueEntry(String line, BufferedReader reader) {
            this.line = line;
            this.reader = reader;
        }

        public String getLine() {
            return line;
        }

        public BufferedReader getReader() {
            return reader;
        }
    }

    public static void main(String[] args) {
        try {
            burstsort("input.txt", "output", 100);
            mergeFiles("output", "sorted_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
