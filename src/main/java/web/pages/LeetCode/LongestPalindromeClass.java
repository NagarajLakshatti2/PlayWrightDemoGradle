package web.pages.LeetCode;

public class LongestPalindromeClass {
    static void main(String[] args) {



    }
    public String longestPalindrome(String s){
        String longest = "";

        for(int i = s.length() - 1; i >= 0; i--){

            for(int j = 0; j <= i; j++) {
                String str = s.substring(j, i + 1);

                String rev = "";

//                for (int k = str.length() - 1; k >= 0; k--) {
//                    rev = rev + str.charAt(k);
//                }

                for(char c: str.toCharArray()) {
                    rev += String.valueOf(c);
                }

                if (str.equals(rev)) {
                    if(str.length() > longest.length()) {
                        longest = str;
                    }
                }
            }
        }
        return longest;
    }
}
