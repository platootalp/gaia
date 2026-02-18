//给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,5,11,5]
//输出：true
//解释：数组可以分割成 [1, 5, 5] 和 [11] 。 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,5]
//输出：false
//解释：数组不能分割成两个元素和相等的子集。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 200 
// 1 <= nums[i] <= 100 
// 
//
// Related Topics 数组 动态规划 👍 1712 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean canPartition(int[] nums) {
        // 剪枝操作
        int n = nums.length;
        if(n<2){
            return false;
        }
        int sum = 0;
        int maxNum = 0;
        for (int num : nums) {
            sum+=num;
            maxNum = Math.max(num,maxNum);
        }
        int target = sum>>1;
        if(sum%2!=0 || maxNum>target){// 数组中的和为奇数或者最大值大于sum/2
            return  false;
        }

        // 状态转移
        boolean[] dp = new boolean[target+1];
        dp[0] = true;
        for(int i=0;i<n;i++){
            for(int j = target;j>=nums[i];j--){
                dp[j] |= dp[j-nums[i]];
            }
        }

        return dp[target];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
