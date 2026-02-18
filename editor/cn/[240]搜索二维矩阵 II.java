//编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性： 
//
// 
// 每行的元素从左到右升序排列。 
// 每列的元素从上到下升序排列。 
// 
//
// 
//
// 示例 1： 
// 
// 
//输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21
//,23,26,30]], target = 5
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21
//,23,26,30]], target = 20
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= n, m <= 300 
// -10⁹ <= matrix[i][j] <= 10⁹ 
// 每行的所有元素从左到右升序排列 
// 每列的所有元素从上到下升序排列 
// -10⁹ <= target <= 10⁹ 
// 
//
// Related Topics 数组 二分查找 分治 矩阵 👍 1250 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 二分查找、Z字形查找（O(m+n)）
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int x = 0, y = n - 1;
        while (x < m && y >= 0) {
            if (matrix[x][y] == target) {
                return true;
            }
            if (matrix[x][y] > target) {
                --y;
            } else {
                ++x;
            }
        }
        return false;
    }
//    public boolean searchMatrix(int[][] matrix, int target) {
//        int m = matrix.length;
//        int n = matrix[0].length;
//
//        // 找在哪一列上
//        for(int i=0;i<m;i++){
//            int low = 0;
//            int high = n-1;
//            int mid = (low+high)/2;
//            while(low<=high){
//                if(target<matrix[i][mid]){
//                    high = mid-1;
//                }
//                else if(target>matrix[i][mid]){
//                    low = mid+1;
//                }
//                else {
//                    return true;
//                }
//                mid = (low+high)/2;
//            }
//        }
//        return false;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
