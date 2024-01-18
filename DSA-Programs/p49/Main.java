import java.util.Arrays;

class BloomFilter {
    // hash 1
    private static int h1(String s, int arrSize) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash + ((int) s.charAt(i))) % arrSize;
        }
        return (int) hash;
    }

    // hash 2
    private static int h2(String s, int arrSize) {
        long hash = 1;
        for (int i = 0; i < s.length(); i++) {
            hash = (long) ((hash + Math.pow(19, i) * s.charAt(i)) % arrSize);
        }
        return (int) (hash % arrSize);
    }

    // hash 3
    private static int h3(String s, int arrSize) {
        long hash = 7;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31 + s.charAt(i)) % arrSize;
        }
        return (int) (hash % arrSize);
    }

    // hash 4
    private static int h4(String s, int arrSize) {
        long hash = 3;
        int p = 7;
        for (int i = 0; i < s.length(); i++) {
            hash += hash * 7 + s.charAt(0) * Math.pow(p, i);
            hash = hash % arrSize;
        }
        return (int) hash;
    }

    // lookup operation
    private static boolean lookup(boolean[] bitArray, int arrSize, String s) {
        int a = h1(s, arrSize);
        int b = h2(s, arrSize);
        int c = h3(s, arrSize);
        int d = h4(s, arrSize);

        return bitArray[a] && bitArray[b] && bitArray[c] && bitArray[d];
    }

    // insert operation
    public static void insert(boolean[] bitArray, int arrSize, String s) {
        // check if the element is already present or not
        if (lookup(bitArray, arrSize, s)) {
            System.out.println(s + " is Probably already present");
        } else {
            int a = h1(s, arrSize);
            int b = h2(s, arrSize);
            int c = h3(s, arrSize);
            int d = h4(s, arrSize);

            bitArray[a] = true;
            bitArray[b] = true;
            bitArray[c] = true;
            bitArray[d] = true;

            System.out.println(s + " inserted");
        }
    }

    
    
}


public class Main {

    public static void main(String[] args) {
        boolean[] bitArray = new boolean[100];
        int arrSize = 100;
        String[] sArray = {
                "abound", "abounds", "abundance",
                "abundant", "accessible", "bloom",
                "blossom", "bolster", "bonny",
                "bonus", "bonuses", "coherent",
                "cohesive", "colorful", "comely",
                "comfort", "gems", "generosity",
                "generous", "generously", "genial",
                "bluff", "cheater", "hate",
                "war", "humanity", "racism",
                "hurt", "nuke", "gloomy",
                "facebook", "geeksforgeeks", "twitter"
        };

        Arrays.fill(bitArray, false);

        for (String value : sArray) {
            BloomFilter.insert(bitArray, arrSize, value);
        }
    }
}

