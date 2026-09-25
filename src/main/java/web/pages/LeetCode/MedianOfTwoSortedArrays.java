package web.pages.LeetCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//4. Median of Two Sorted Arrays
//        Solved
//Hard
//        Topics
//premium lock icon
//        Companies
//Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
//
//The overall run time complexity should be O(log (m+n)).
//
//
//
//Example 1:
//
//Input: nums1 = [1,3], nums2 = [2]
//Output: 2.00000
//Explanation: merged array = [1,2,3] and median is 2.
//Example 2:
//
//Input: nums1 = [1,2], nums2 = [3,4]
//Output: 2.50000
//Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
//
//
//Constraints:
//nums1.length == m
//nums2.length == n
//0 <= m <= 1000
//        0 <= n <= 1000
//        1 <= m + n <= 2000
//        -106 <= nums1[i], nums2[i] <= 106

public class MedianOfTwoSortedArrays {

    public static double findMedian(int[] nums1, int[] nums2) {

        List<Integer> list = new ArrayList<>();

        for (int n : nums1)
            list.add(n);

        for (int n : nums2)
            list.add(n);

        Collections.sort(list);

        int n = list.size();

        if (n % 2 != 0)
            return list.get(n / 2);

        return (list.get(n / 2 - 1) + list.get(n / 2)) / 2.0;
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};
        System.out.println(findMedian(nums1, nums2));

        nums1 = new int[]{1, 3};
        nums2 = new int[]{2};
        System.out.println(findMedian(nums1, nums2));
    }
}
