//给定一个整数数组
// prices，其中第 prices[i] 表示第 i 天的股票价格 。 
//
// 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）: 
//
// 
// 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。 
// 
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
//
// 
//
// 示例 1: 
//
// 
//输入: prices = [1,2,3,0,2]
//输出: 3 
//解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出] 
//
// 示例 2: 
//
// 
//输入: prices = [1]
//输出: 0
// 
//
// 
//
// 提示： 
//
// 
// 1 <= prices.length <= 5000 
// 0 <= prices[i] <= 1000 
// 
//
// Related Topics 数组 动态规划 👍 1464 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 动态规划
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] f = new int[3][n];

        // 边界条件
        // f[0][..]表示当前持有股票的情况下的最大利润
        // f[1][..]表示当前不持有股票且不在冷静期的情况下的最大利润
        // f[2][..]表示当前不持有股票且在冷静期的情况下的最大利润
        int f0 = -prices[0];
        int f1 = 0;
        int f2 = 0;

        // 转移方程
        for(int i=1;i<n;i++){
            int tf0 = f0;
            int tf1 = f1;
            int tf2 = f2;
            f0 = Math.max(tf0,tf1-prices[i]);
            f1 = Math.max(tf1,tf2);
            f2 = tf0 + prices[i];
        }

        return Math.max(f1,f2);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
