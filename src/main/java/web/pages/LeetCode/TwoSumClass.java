package web.pages.LeetCode;

//You are given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
//You may assume that each input would have exactly one solution, and you may not use the same element twice.
//You can return the answer in any order.
//
//Example 1:
//Input: nums = [2,7,11,15], target = 9
//Output: [0,1]
//Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
//
//Example 2:
//Input: nums = [3,2,4], target = 6
//Output: [1,2]
//Example 3:
//
//Input: nums = [3,3], target = 6
//Output: [0,1]
//
//
//Constraints:
//        2 <= nums.length <= 104
//        -109 <= nums[i] <= 109
//        -109 <= target <= 109
//Only one valid answer exists.
//
//Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?



public class TwoSumClass {
    public int sum = 0;

    public static void main(String[] args) {

        TwoSumClass twosumclass = new TwoSumClass();

        // use below int array and get two number add those and check with target and then return those two array index only
        int[] nums1 = new int[]{2,7,11,15};
        int target1 = 9;

        int[] nums2 = new int[]{3,2,4};
        int target2 = 6;

        int[] nums3 = new int[]{3,3};
        int target3 = 6;

        int[] Tval1 = twosumclass.twoSum(nums1, target1);
        int[] Tval2 = twosumclass.twoSum(nums2, target2);
        int[] Tval3 = twosumclass.twoSum(nums3, target3);

        int sumTarget1 = nums1[Tval1[0]] + nums1[Tval1[1]];

        if(sumTarget1 == target1) {
            System.out.println("Tval1 0 is : "+Tval1[0] +  " Tval1 1 is :"+ Tval1[1]);
            System.out.println("both same: "+ sumTarget1 + " == " +target1);
        }

        int sumTarget2 = nums2[Tval2[0]] + nums2[Tval2[1]];

        if(sumTarget2 == target2) {
            System.out.println("Tval2 0 is : "+Tval2[0] +  " Tval2 1 is :"+ Tval2[1]);
            System.out.println("both same: "+ sumTarget2 + " == " +target2);
        }

        int sumTarget3 = nums3[Tval3[0]] + nums3[Tval3[1]];

        if(sumTarget3 == target3) {
            System.out.println("Tval3 0 is : "+Tval3[0] +  " Tval3 1 is :"+ Tval3[1]);
            System.out.println("both same: "+ sumTarget2 + " == " +target2);
        }
    }
    // return index of two value witch match to target value
    // declare int with 2 size
    // two for loop one for first index and val and second loop is second index and val then
    // add those two match with target

    public int[] twoSum(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++) {

            for(int j = i+1; j < nums.length; j++) {

                sum = nums[i] + nums[j];

                if(sum == target) {
                    System.out.println("sum: "+sum + " == "+ target);
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
}
