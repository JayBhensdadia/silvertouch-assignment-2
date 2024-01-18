

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

class HuffmanTree {
    private HuffmanNode root;

    public HuffmanTree(Map<Character, Integer> frequencyMap) {
        buildTree(frequencyMap);
    }

    private void buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode internalNode = new HuffmanNode('\0', left.frequency + right.frequency);
            internalNode.left = left;
            internalNode.right = right;

            priorityQueue.offer(internalNode);
        }

        root = priorityQueue.poll();
    }

    public Map<Character, String> buildCodeTable() {
        Map<Character, String> codeTable = new HashMap<>();
        buildCodeTable(root, "", codeTable);
        return codeTable;
    }

    private void buildCodeTable(HuffmanNode node, String code, Map<Character, String> codeTable) {
        if (node != null) {
            if (node.data != '\0') {
                codeTable.put(node.data, code);
            }

            buildCodeTable(node.left, code + "0", codeTable);
            buildCodeTable(node.right, code + "1", codeTable);
        }
    }

    public HuffmanNode getRoot() {
        return root;
    }
}

public class HuffmanCoding {

    public static String compress(String text) {
        Map<Character, Integer> frequencyMap = calculateFrequency(text);
        HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
        Map<Character, String> codeTable = huffmanTree.buildCodeTable();

        StringBuilder compressedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedText.append(codeTable.get(c));
        }

        return compressedText.toString();
    }

    public static String decompress(String compressedText, HuffmanNode root) {
        StringBuilder decompressedText = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : compressedText.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            if (current.data != '\0') {
                decompressedText.append(current.data);
                current = root; 
            }
        }

        return decompressedText.toString();
    }

    private static Map<Character, Integer> calculateFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    public static void main(String[] args) {
        String originalText = "abracadabra";
        System.out.println("Original Text: " + originalText);

        
        String compressedText = compress(originalText);
        System.out.println("Compressed Text: " + compressedText);

        
        String decompressedText = decompress(compressedText, new HuffmanTree(calculateFrequency(originalText)).getRoot());
        System.out.println("Decompressed Text: " + decompressedText);
    }
}

