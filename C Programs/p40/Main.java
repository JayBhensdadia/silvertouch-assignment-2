import java.util.PriorityQueue;
import java.util.HashMap;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

public class Main {
    public static void main(String[] args) {
        String inputText = "hello world";
        String compressedText = compress(inputText);
        System.out.println("Original Text: " + inputText);
        System.out.println("Compressed Text: " + compressedText);
        System.out.println("Decompressed Text: " + decompress(compressedText));
    }

    public static String compress(String text) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (char c : frequencyMap.keySet()) {
            priorityQueue.offer(new HuffmanNode(c, frequencyMap.get(c)));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode combined = new HuffmanNode('\0', left.frequency + right.frequency);
            combined.left = left;
            combined.right = right;
            priorityQueue.offer(combined);
        }

        HuffmanNode root = priorityQueue.poll();
        HashMap<Character, String> codeMap = new HashMap<>();
        buildCodeMap(root, "", codeMap);

        StringBuilder compressedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedText.append(codeMap.get(c));
        }

        return compressedText.toString();
    }

    private static void buildCodeMap(HuffmanNode node, String code, HashMap<Character, String> codeMap) {
        if (node == null) {
            return;
        }

        if (node.data != '\0') {
            codeMap.put(node.data, code);
        }

        buildCodeMap(node.left, code + "0", codeMap);
        buildCodeMap(node.right, code + "1", codeMap);
    }

    public static String decompress(String compressedText) {
        StringBuilder decompressedText = new StringBuilder();
        HuffmanNode root = buildHuffmanTree(compressedText);
        HuffmanNode current = root;

        for (char bit : compressedText.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decompressedText.append(current.data);
                current = root;
            }
        }

        return decompressedText.toString();
    }

    private static HuffmanNode buildHuffmanTree(String compressedText) {
        HuffmanNode root = new HuffmanNode('\0', 0);
        HuffmanNode current = root;

        for (char bit : compressedText.toCharArray()) {
            if (bit == '0') {
                if (current.left == null) {
                    current.left = new HuffmanNode('\0', 0);
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new HuffmanNode('\0', 0);
                }
                current = current.right;
            }
        }

        return root;
    }
}
