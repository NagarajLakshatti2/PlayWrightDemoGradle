package web.pages.LeetCode;

public class VerifyIsPalindrome {

    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        int rev = 0;
        int cp_x = x;

        while(x !=0) {
            int lastDigit = x % 10;
            rev = rev * 10 + lastDigit;
            x = x / 10;
        }

        if(rev == cp_x) {
            return true;
        } else {
            return false;
        }
    }
}
