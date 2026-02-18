//给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的连续子数组的个数 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,1], k = 2
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3], k = 3
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 10⁴ 
// -1000 <= nums[i] <= 1000 
// -10⁷ <= k <= 10⁷ 
// 
//
// Related Topics 数组 哈希表 前缀和 👍 1887 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] preSum = new int[n+1];
        preSum[0] = 0;
        for(int i=1;i<=n;i++){
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        int count = 0;
        for(int i=0;i<n;i++){
            for(int j=1;j<=n-i;j++){// 遍历长度
                int sum = preSum[i+j] - preSum[i];
                if(sum==k){
                    count++;
                }
            }
        }
        return count;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
