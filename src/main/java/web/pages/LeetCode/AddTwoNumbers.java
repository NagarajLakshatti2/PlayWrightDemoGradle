package web.pages.LeetCode;
///*
//You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
//You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//
//Example 1:
//
//Input: l1 = [2,4,3],
//       l2 = [5,6,4] (4 + 6 = 10 so removed or carry 1 to next remain is 0) and 3 + 4 = 7 + 1(carry) = 8
//    Output: [7,0,8]
//Explanation: 342 + 465 = 807.
//Example 2:
//
//Input: l1 = [0], l2 = [0]
//Output: [0]
//Example 3:
//
//Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
//Output: [8,9,9,9,0,0,0,1]
//
//Constraints:
//
//The number of nodes in each linked list is in the range [1, 100].
//0 <= Node.val <= 9
//It is guaranteed that the list represents a number that does not have leading zeros.
//**/

import java.util.LinkedList;

public class AddTwoNumbers {
    public static void main(String[] args) {

        //  get first number firstlinkedList and second from second list and add those check is single digit output fine, if not get carry firstnumber of two digits and second number is fine,
        //
        // 342 = 2 -> 4 -> 3
        LinkedList<Integer> l1 = new LinkedList<>();
        l1.add(2);
        l1.add(4);
        l1.add(3);

        // 465 = 5 -> 6 -> 4
        LinkedList<Integer> l2 = new LinkedList<>();
        l2.add(5);
        l2.add(6);
        l2.add(4);

        LinkedList<Integer> result = addTwoNumbers(l1, l2);

        System.out.println("List 1: " + l1);
        System.out.println("List 2: " + l2);
        System.out.println("Result: " + result);

        m1();
    }

    public static LinkedList<Integer> addTwoNumbers(LinkedList<Integer> l1, LinkedList<Integer> l2) {

        LinkedList<Integer> result = new LinkedList<>();

        int carry = 0;
        int i = 0;

        while (i < l1.size() || i < l2.size() || carry != 0) {

//            int value1 = (i < l1.size()) ? l1.get(i) : 0;
//            int value2 = (i < l2.size()) ? l2.get(i) : 0;

            int value1 = !l1.isEmpty() ? l1.removeFirst() : 0;
            int value2 = !l2.isEmpty() ? l2.removeFirst() : 0;

            int sum = value1 + value2 + carry;

            int digit = sum % 10;
            carry = sum / 10;

            result.add(digit);

            i++;
        }

        return result;
    }

    public static void m1() {
        // 342 → [2, 4, 3]
        // 465 → [5, 6, 4]
        //==================
        // +      7, 0, 8

        // Input: l1 = [9,9,9,9,9,9,9],
        //        l2 = [9,9,9,9]
        //         +    8,9,9,9,0,0,0,1
        //////
        // Output: [8,9,9,9,0,0,0,1]

        int[] ll1 = new int[]{3,4,2};
        int[] ll2 = new int[]{4,6,5};

        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        for (int n1:ll1)
            l1.add(n1);

        for(int n2:ll2)
            l2.add(n2);

        LinkedList<Integer> result = addTwoNumbers2(l1,l2);
        System.out.println("result: "+result);
    }

    public static LinkedList<Integer> addTwoNumbers2(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        LinkedList<Integer> result = new LinkedList<>();
        int carry = 0;
        int i =0;

        while (i < l1.size() || i < l2.size() || carry !=0) {
            int val1 = !l1.isEmpty() ? l1.removeFirst():0;
            int val2 = !l2.isEmpty() ? l2.removeFirst():0;

            int sum = val1 + val2 + carry;

            int lastDigits = sum % 10;
            carry = sum / 10;

            result.add(lastDigits);

            i++;

        }
        return result;
    }

    }