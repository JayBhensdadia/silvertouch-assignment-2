public class Main {

    public static int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }

        int sign = 1;
        int result = 0;
        int i = 0;

        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            sign = (str.charAt(0) == '-') ? -1 : 1;
            i++;
        }

        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            int digit = str.charAt(i) - '0';

            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            i++;
        }

        return result * sign;
    }

    public static void main(String[] args) {
        String str1 = "42";
        System.out.println("String: " + str1 + ", Integer: " + myAtoi(str1));

        String str2 = "   -42";
        System.out.println("String: " + str2 + ", Integer: " + myAtoi(str2));

        String str3 = "4193 with words";
        System.out.println("String: " + str3 + ", Integer: " + myAtoi(str3));

        String str4 = "words and 987";
        System.out.println("String: " + str4 + ", Integer: " + myAtoi(str4));

        String str5 = "-91283472332";
        System.out.println("String: " + str5 + ", Integer: " + myAtoi(str5));
    }
}
