public class Palindromes {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String word = args[i];
            if (isPalindrome(word)) {
                System.out.println(word + "-> palindrome");
            } else {
                System.out.println(word + "-> not palindrome");
            }
        }
    }

    public static String reverseString(String word) {
        String reversed = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            reversed += word.charAt(i);
        }
        return reversed;
    }

    public static boolean isPalindrome(String word) {
        String reversed = reverseString(word);
        return word.equals(reversed);
    }
}
