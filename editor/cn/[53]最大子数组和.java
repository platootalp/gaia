//给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。 
//
// 子数组 是数组中的一个连续部分。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出：6
//解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1]
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：nums = [5,4,-1,7,8]
//输出：23
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// 
//
// 
//
// 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。 
//
// Related Topics 数组 分治 动态规划 👍 5846 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 动态规划（线性dp）
    public int maxSubArray(int[] nums) {// 动态规划
        int n = nums.length;
        int ans = nums[0];
        int f = nums[0];// 记录以i结尾的连续子数组的最大和

        for(int i=1; i<n;i++){
            f = Math.max(f+nums[i], nums[i]);// 为上一个位置的最大和与当前数值相加后的值与当前值中的较大值
            ans = Math.max(f, ans);// 各个位置结尾的最大和中的最大值
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
