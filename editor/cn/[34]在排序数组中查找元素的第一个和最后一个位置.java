//给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。 
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
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
// Related Topics 数组 二分查找 👍 2200 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 二分查找
    public int[] searchRange(int[] nums, int target) {
        int len = nums.length;
        if(len==0)return new int[]{-1,-1};
        if(len==1)return nums[0]==target ? new int[]{0,0} : new int[]{-1,-1};

        int l=0,r=len-1;
        int mid = (l+r)/2;
        while(l<=r){
            if(nums[mid]>target){
                r = mid-1;
                mid = (l+r)/2;
            }
            else if(nums[mid]<target){
                l = mid+1;
                mid = (l+r)/2;
            }
            else{// TODO 此处可优化
                int start, end;
                start = end = mid;
                while(start>=0){// 找到给定目标值在数组中的开始位置
                    if(nums[start]!=target)break;
                    start--;
                }
                while(end<=len-1){// 找到给定目标值在数组中的结束位置
                    if(nums[end]!=target)break;
                    end++;
                }
                return new int[]{start+1, end-1};
            }
        }

        return new int[]{-1,-1};
    }
}
//leetcode submit region end(Prohibit modification and deletion)
