/*
58. Length of Last Word
Given a string s consisting of words and spaces, return the length of the last word in the string.

A word is a maximal substring consisting of non-space characters only.

Example 1:
Input: s = "Hello World"
Output: 5
Explanation: The last word is "World" with length 5.

Example 2:
Input: s = "   fly me   to   the moon  "
Output: 4
Explanation: The last word is "moon" with length 4.

Example 3:
Input: s = "luffy is still joyboy"
Output: 6
Explanation: The last word is "joyboy" with length 6.


Constraints:
1 <= s.length <= 104
s consists of only English letters and spaces ' '.
There will be at least one word in s.
* */

package web.pages.LeetCode;

public class LengthOfLastWordOFString {

    static void main() {
        String s1 = "Hello World";
        int len1 = s1.split(" ")[s1.split(" ").length - 1].length();
        System.out.println(lengthOfLastWord(s1) + "  : "+len1);

        String s2 = "   fly me   to   the moon  ";
        int len2 = s2.split(" ")[s2.split(" ").length - 1].length();
        System.out.println(lengthOfLastWord(s2)+ "  : "+len2);

        String s3 = "luffy is still joyboy";
        int len3 = s3.split(" ")[s3.split(" ").length - 1].length();
        System.out.println(lengthOfLastWord(s3)+ "  : "+len3);
    }
    public static int lengthOfLastWord(String s) {

        String str[] = s.split(" ");

        int lastWordLen = str[str.length - 1].length();

        return lastWordLen;
    }
}
