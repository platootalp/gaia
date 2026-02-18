//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。 
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。 
//
// 
//
// 例如，在下面的 3×4 的矩阵中包含单词 "ABCCED"（单词中的字母已标出）。 
//
// 
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
//输入：board = [["a","b"],["c","d"]], word = "abcd"
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
// 注意：本题与主站 79 题相同：https://leetcode-cn.com/problems/word-search/ 
//
// Related Topics 数组 回溯 矩阵 👍 783 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        int len = word.length();

        boolean[][] visited = new boolean[m][n];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if(dfs(board,word,i,j,0,visited)){
                    return true;
                }

            }
        }

        return false;
    }
    public boolean dfs(char[][] board, String word,int i, int j, int index, boolean[][] visited){
        if(board[i][j]!=word.charAt(index)){
            return false;
        }
        else if(index == word.length()-1){
            return true;
        }

        int[][] directions ={{-1,0},{0,1},{1,0},{0,-1}};// 上右下左
        visited[i][j] = true;
        for (int[] d : directions) {
            int newi = i + d[0];
            int newj = j + d[1];
            if(newi>=0 && newi<board.length && newj>=0 && newj<board[0].length){
                if(!visited[newi][newj]){
                    if(dfs(board,word,newi,newj,index+1,visited)){
                        return true;
                    }
                }
            }
        }
        visited[i][j] = false;
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
