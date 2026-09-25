package web.pages.LeetCode;

//        Given a string s, find the length of the longest substring without duplicate characters.
//        Example 1:
//
//        Input: s = "abcabcbb"
//        Output: 3
//        Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.
//        Example 2:
//
//        Input: s = "bbbbb"
//        Output: 1
//        Explanation: The answer is "b", with the length of 1.
//        Example 3:
//
//        Input: s = "pwwkew"
//        Output: 3
//        Explanation: The answer is "wke", with the length of 3.
//        Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

//        Constraints:
//
//        0 <= s.length <= 105
//        s consists of English letters, digits, symbols and spaces.

import java.util.HashSet;

public class LongestSubstring {

    public static int lengthOfLongestSubstring(String s) {

        HashSet<Character> set1 = new HashSet<>();
        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            System.out.println("loop start: "+set);
            set1.add(s.charAt(right));

            while(set.contains(s.charAt(right))) {
                System.out.println("While loop start: "+set);
                set.remove(s.charAt(left));
                left++;
                System.out.println("While loop end: "+set);
            }
            set.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
            System.out.println("loop end: "+set);
        }
        System.out.println("str end: "+set1);

        return maxLength;
    }

    public static void main(String[] args) {

        String s = "abcabcbb";

        int result = lengthOfLongestSubstring(s);

        System.out.println("Longest substring length = " + result);
    }
}
