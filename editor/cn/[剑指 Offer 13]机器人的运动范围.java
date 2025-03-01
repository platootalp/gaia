//地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一
//格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但
//它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？ 
//
// 
//
// 示例 1： 
//
// 输入：m = 2, n = 3, k = 1
//输出：3
// 
//
// 示例 2： 
//
// 输入：m = 3, n = 1, k = 0
//输出：1
// 
//
// 提示： 
//
// 
// 1 <= n,m <= 100 
// 0 <= k <= 20 
// 
//
// Related Topics 深度优先搜索 广度优先搜索 动态规划 👍 624 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    int count = 0;
    boolean[][] visited;
    public int movingCount(int m, int n, int k) {
        visited = new boolean[m][n];
        dfs(0,0,m,n,k);
        return count;
    }
    public void dfs(int i,int j, int m, int n, int k){
        if(i>=0 && i<m && j>=0 && j<n && !visited[i][j] ){
            if(check(i,j,k)){
                count++;
            }
            else return;
        }
        else return;

        visited[i][j] = true;
        int[][] direction = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
        for (int[] dir : direction) {
            int newi = i+dir[0];
            int newj = j+dir[1];
            dfs(newi,newj,m,n,k);
        }
        return;
    }
    public boolean check(int i,int j,int k){
        int sum = 0;
        while (i!=0){
            sum+=i%10;
            i /= 10;
        }
        while (j!=0){
            sum+=j%10;
            j /= 10;
        }
        return sum<=k;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
