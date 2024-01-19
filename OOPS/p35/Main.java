import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String regex = "\\b\\d{3}-\\d{2}-\\d{4}\\b";

        Pattern pattern = Pattern.compile(regex);

        String text1 = "John's SSN is 123-45-6789.";
        String text2 = "Alice's SSN is 987-65-4321.";

        Matcher matcher1 = pattern.matcher(text1);
        Matcher matcher2 = pattern.matcher(text2);

        if (matcher1.find()) {
            System.out.println("Match found in text1: " + matcher1.group());
        } else {
            System.out.println("No match found in text1.");
        }

        if (matcher2.find()) {
            System.out.println("Match found in text2: " + matcher2.group());
        } else {
            System.out.println("No match found in text2.");
        }
    }
}
