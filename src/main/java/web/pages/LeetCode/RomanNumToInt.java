package web.pages.LeetCode;

public class RomanNumToInt {

    public static void main() {
        int expected1 = 3;
        String s1 = "III";
        System.out.println(romanToInt(s1)  +" Expected: "+ expected1);

        int expected2 = 58;
        String s2 = "LVIII";
        System.out.println(romanToInt(s2) +" Expected: "+ expected2);

        int expected3 = 1994;
        String s3 = "MCMXCIV";
        System.out.println(romanToInt(s3) +" Expected: "+ expected3);
    }

    public static int romanToInt(String s) {
        int romSum = 0;

        for(int i = 0; i < s.length(); i++){

            int current = getValue(s.charAt(i));

            if(i + 1 < s.length()) {

                int next = getValue(s.charAt(i + 1));

                if(current < next){
                    romSum = romSum - current;
                } else {
                    romSum = romSum + current;
                }
            } else {
                romSum = romSum + current;
            }
        }
        return romSum;
    }
    private static int getValue(char c) {

        switch(c) {
            case 'I':
                return 1;

            case 'V':
                return 5;

            case 'X':
                return 10;

            case 'L':
                return 50;

            case 'C':
                return 100;

            case 'D':
                return 500;

            case 'M':
                return 1000;

            default:
                return 0;
        }

    }

}
