import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int start = 0;

        while (start < words.length) {
            int end = findEndIndex(words, start, maxWidth);
            result.add(justifyLine(words, start, end, maxWidth));
            start = end + 1;
        }

        return result;
    }

    private static int findEndIndex(String[] words, int start, int maxWidth) {
        int end = start;
        int sum = words[end].length();

        while (end + 1 < words.length && sum + words[end + 1].length() + 1 <= maxWidth) {
            end++;
            sum += words[end].length() + 1;
        }

        return end;
    }

    private static String justifyLine(String[] words, int start, int end, int maxWidth) {
        StringBuilder line = new StringBuilder();
        int totalSpaces = maxWidth - countLength(words, start, end);

        if (end == start || end == words.length - 1) {
            for (int i = start; i <= end; i++) {
                line.append(words[i]);
                if (i < end) {
                    appendSpaces(line, 1);
                }
            }
            appendSpaces(line, totalSpaces - (end - start));
        } else {
            int gaps = end - start;
            int spacesPerGap = totalSpaces / gaps;
            int extraSpaces = totalSpaces % gaps;

            for (int i = start; i <= end; i++) {
                line.append(words[i]);
                if (i < end) {
                    int spaces = spacesPerGap + ((i - start) < extraSpaces ? 1 : 0);
                    appendSpaces(line, spaces);
                }
            }
        }

        return line.toString();
    }

    private static int countLength(String[] words, int start, int end) {
        int length = 0;
        for (int i = start; i <= end; i++) {
            length += words[i].length();
        }
        return length;
    }

    private static void appendSpaces(StringBuilder line, int count) {
        for (int i = 0; i < count; i++) {
            line.append(' ');
        }
    }

    public static void main(String[] args) {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth = 16;

        List<String> justifiedText = fullJustify(words, maxWidth);

        for (String line : justifiedText) {
            System.out.println(line);
        }
    }
}
