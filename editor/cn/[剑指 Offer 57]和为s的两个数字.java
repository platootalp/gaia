//输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。 
//
// 
//
// 示例 1： 
//
// 输入：nums = [2,7,11,15], target = 9
//输出：[2,7] 或者 [7,2]
// 
//
// 示例 2： 
//
// 输入：nums = [10,26,30,31,47,60], target = 40
//输出：[10,30] 或者 [30,10]
// 
//
// 
//
// 限制： 
//
// 
// 1 <= nums.length <= 10^5 
// 1 <= nums[i] <= 10^6 
// 
//
// Related Topics 数组 双指针 二分查找 👍 253 👎 0


import java.util.HashMap;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high = n-1;
        while(low<n && low<=high){
            int sum = nums[low]+nums[high];
            if(sum>target){
                high--;
            }
            else if(sum<target){
                low++;
            }
            else{
                return new int[]{nums[low],nums[high]};
            }
        }
        return null;
    }
//    public int[] twoSum(int[] nums, int target) {
//        int n = nums.length;
//        for(int i=0;i<n-1;i++){
//            int tar = target-nums[i];
//            int low = i+1, high = n-1;
//            int mid = (low+high)>>1;
//            while(low<n && low<=high){
//                if(nums[mid]>tar){
//                    high = mid-1;
//                }
//                else if(nums[mid]<tar){
//                    low = mid+1;
//                }
//                else{
//                    return new int[]{nums[i],nums[mid]};
//                }
//                mid = (low+high)>>1;
//            }
//        }
//        return null;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
