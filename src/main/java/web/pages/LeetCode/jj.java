package web.pages.LeetCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class jj {
    private static final Logger log = LoggerFactory.getLogger(jj.class);

    public static void main(String[] args) {
        Integer a = 126;
        Integer b = 126;
        System.out.println(a == b); // t

        Integer a1 = 200;
        Integer b1 = 200;
        System.out.println(a1 == b1); // f

        Integer b11 = 200;
        int k = 200;
        System.out.println(k == b11);

        int t = 100;
        int j = 100;
        System.out.println(t == j);

        int t1 = 200;
        int j1 = 200;
        System.out.println(t1 == j1);


//        findDuplicateWord("What is your name ? My name is Naga");
//        findDuplicateChar("gadag");
//        reversString("gadag");
//        reverseNumber(122221);
          reverseNumber(120);
//        reverseNumber(12222111);
//        find_Duplicate_Chara_From_Sentence_Of_Every_Words("For an SDET/Java interview, I'd recommend knowing the first approach and the mathematical % 10 approach, because interviewers commonly ask you to reverse a number without converting it to String. Java interviewers without reverse without ok lo pjsg without");
//        isReverNumber(121);
//        isReverNumber(1211);
    }


    /*
      String Sentence split by space using HashMap get String word as key and get count of those words and get duplicate
      using Map.Entry, so using getOrDefault

     */
    public static void findDuplicateWord(String words) {
        String str[] = words.split(" ");

        Map<String, Integer> hm = new HashMap<>();

        for(String word: str){
           hm.put(word, hm.getOrDefault(word,0) +1);
        }

        for(Map.Entry<String, Integer> entry: hm.entrySet()){
            if(entry.getValue() > 1) {
                System.out.println("Key: "+entry.getKey() + ", Count of word as same: "+entry.getValue());
            }
//            else {
//                System.out.println("Key: "+entry.getKey() + ", Count of word as same: "+entry.getValue());
//            }
        }
    }

    public static void findDuplicateChar(String word) {

//        char[] ch  = word.toCharArray();
        HashMap<Character, Integer> hm = new HashMap<>();

        for(char c: word.toCharArray()) {
            hm.put(c, hm.getOrDefault(c, 0) +1);
        }

        for(Map.Entry<Character, Integer> entry: hm.entrySet()) {
            if(entry.getValue() > 1) {
                log.info("Key: "+entry.getKey() + ", Count of char as same: "+entry.getValue());
            } else {
                log.info("Key: "+entry.getKey() + ", Count of char as same: "+entry.getValue());
            }
        }
    }

    public static void reversString(String string) {
        String rv = "";
        for(int i = string.length()-1; i>=0; i--) {
            rv += string.charAt(i);
        }

        System.out.println("rv: "+rv);
        if(rv.equals(string)) {
          System.out.println("Given String is Palindrome");
        } else {
          System.out.println("Given String is Not Palindrome");
        }
    }

    public static void reverseNumber(int num) {
        int cp_Num = num;
        int revNum = 0;
        while(num !=0) {
            int lastDigit = num % 10;
            revNum = revNum * 10 + lastDigit;
            num = num / 10;
        }
        System.out.println("Reversed number: " + revNum);
        if(cp_Num == revNum)
            System.out.println("Given number is reversed and Palindrome: " +revNum);
        else
            System.out.println("Given number is reversed and its not palindrome: "+revNum);
    }

    public static void find_Duplicate_Chara_From_Sentence_Of_Every_Words(String words) {
        String srwords[] = words.split(" ");
        int cunt = 0;
        Map<Character, Integer> hm;
        Map<String, Integer> shm = new HashMap<>();

        for(String str: srwords) {
            cunt = cunt + 1;
            System.out.println("================ Start Word is for duplicate char count ====================");
            System.out.println("                       word is: "+ str + " index: "+ cunt);
            System.out.println("================ ====================================== ====================");

            shm.put(str, shm.getOrDefault(str, 0)+1);

            hm = new HashMap<>();

            for (char ch : str.toCharArray()) {
                hm.put(ch, hm.getOrDefault(ch, 0) + 1);
            }
            for (Map.Entry<Character, Integer> entry : hm.entrySet()) {
                    if (entry.getValue() > 0)
                        System.out.println("char Key is: " + entry.getKey() + " char Value is: " + entry.getValue());
            }
            System.out.println(" ============================  End Word is for duplicate char count ========= ===============");
            System.out.println("====================================================== ====================");
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("Start of Each Word duplicate");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        for (Map.Entry<String, Integer> entry : shm.entrySet()) {
            if (entry.getValue() > 0)
                System.out.println("String word Key is: " + entry.getKey() + " String word Value/count is : " + entry.getValue());
        }

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("End of Each Word duplicate");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }
}



