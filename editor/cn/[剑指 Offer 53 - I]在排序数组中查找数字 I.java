//统计一个数字在排序数组中出现的次数。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2 
//
// 示例 2: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
//
// 
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/ 
//
// Related Topics 数组 二分查找 👍 415 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int search(int[] nums, int target){
        int count = 0;
        int low = 0, high = nums.length-1;
        int mid = (low+high)>>1;
        while(low<=high){
            if(nums[mid]<target){
                low = mid+1;
            }
            else if(nums[mid]>target){
                high = mid-1;
            }
            else{// 找到
                count++;
                int l = mid-1,r=mid+1;
                while(l>=0 && nums[l] == target){
                    l--;
                    count++;
                }
                while(r<nums.length && nums[r] == target){
                    r++;
                    count++;
                }
                break;
            }
            mid = (low+high)>>1;
        }

        return count;
    }

//    public int search(int[] nums, int target) {
//        Map<Integer,Integer> map = new HashMap<>();
//
//        for (int num : nums) {
//            map.put(num,map.getOrDefault(num,0)+1);
//        }
//
//        return map.getOrDefault(target,0);
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
