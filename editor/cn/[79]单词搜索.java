//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。 
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。 
//
// 
//
// 示例 1： 
// 
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = 
//"ABCCED"
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = 
//"SEE"
//输出：true
// 
//
// 示例 3： 
// 
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = 
//"ABCB"
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == board.length 
// n = board[i].length 
// 1 <= m, n <= 6 
// 1 <= word.length <= 15 
// board 和 word 仅由大小写英文字母组成 
// 
//
// 
//
// 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？ 
//
// Related Topics 数组 回溯 矩阵 👍 1555 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 回溯(效率低）
    public boolean exist(char[][] board, String word) {
       int m = board.length;
       int n = board[0].length;
       int[][] visited = new int[m][n];// 标记位置是否被访问过

       // 查看board中的每个位置是否能生成word
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                boolean flag = check(board, visited, word, i, j,0);
                if(flag)return true;
            }
        }

        return false;
    }
    public boolean check(char[][] board, int[][] visited, String word,int i, int j, int index){
        if(board[i][j]!=word.charAt(index)){
            return false;
        }
        if(index == word.length()-1){
            return true;
        }

        // 方向选择（上下左右）
        int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};
        visited[i][j] = 1;// 表示当前位置已经被访问了
        for(int[] dir : direction){
            int newi = i+dir[0];
            int newj = j+dir[1];
            if(newi>=0&&newi<board.length&& newj>=0&&newj<board[0].length){
                if(visited[newi][newj]==0){
                    boolean flag = check(board, visited, word, newi, newj, index+1);
                    if(flag)return true;

                }
            }
            else continue;
        }
        visited[i][j] = 0;
        return false;
    }


}
//leetcode submit region end(Prohibit modification and deletion)
