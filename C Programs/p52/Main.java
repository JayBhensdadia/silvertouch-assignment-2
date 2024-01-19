public class Main {

    public static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public static int searchPattern(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        int[] lps = computeLPSArray(pattern);

        int i = 0; 
        int j = 0;

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == m) {
                return i - j; 
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1; 
    }

    public static void main(String[] args) {
        String text = "ABDSABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        int index = searchPattern(text, pattern);

        if (index != -1) {
            System.out.println("Pattern found at index " + index + " in the text.");
        } else {
            System.out.println("Pattern not found in the text.");
        }
    }
}
