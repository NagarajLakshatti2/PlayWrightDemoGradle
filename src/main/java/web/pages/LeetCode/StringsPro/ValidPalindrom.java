/*
 125. Valid Palindrome
        A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward.
        Alphanumeric characters include letters and numbers.
        Given a string s, return true if it is a palindrome, or false otherwise.

        Example 1:
        Input: s = "A man, a plan, a canal: Panama"
        Output: true
        Explanation: "amanaplanacanalpanama" is a palindrome.

        Example 2:
        Input: s = "race a car"
        Output: false
        Explanation: "raceacar" is not a palindrome.

        Example 3:
        Input: s = " "
        Output: true
        Explanation: s is an empty string "" after removing non-alphanumeric characters.
        Since an empty string reads the same forward and backward, it is a palindrome.


        Constraints:
        1 <= s.length <= 2 * 105
        s consists only of printable ASCII characters.
* */


package web.pages.LeetCode.StringsPro;

public class ValidPalindrom {

    static void main() {
        String s1 = "A man, a plan, a canal: Panama";
        System.out.println(isValidPalindrome(s1));

        String s2 = "race a car";
        System.out.println(isValidPalindrome(s2));

        String s3 = " ";
        System.out.println(isValidPalindrome(s3));

        String s4 = "121";
        System.out.println(isValidPalindrome(s4));

        String s5 = "1211";
        System.out.println(isValidPalindrome(s5));
    }

    public static boolean isValidPalindrome(String s) {

        String cps = s.toLowerCase();
        String str = "";
        String strRV = "";

        for (char c: cps.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                str = str + c;
            } else {
            }
        }

        for(int i = str.length()-1; i >= 0; i--) {
            strRV = strRV + str.charAt(i);
        }

        if(str.equals(strRV)) {
            return true;
        } else {
            return false;
        }

    }
}
