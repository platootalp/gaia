//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
//
// Related Topics 数组 回溯 👍 2457 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)

class Solution {
    // 回溯
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> permutation = new ArrayList<>();
        int[] visited = new int[nums.length];// 标记数组，用于记录已经访问过的nums数组值
        backTrace(nums,visited, 0,  permutation, ans);
        return ans;
    }
    public void backTrace(int[] nums, int[] visited, int depth, ArrayList<Integer> permutation, List<List<Integer>> ans){
        if(depth== nums.length) {
            ans.add(new ArrayList<>(permutation));
            return;
        }

        for(int i=0;i<nums.length; i++){
            if(visited[i]==0){// 未被标记
                permutation.add(nums[i]);
                visited[i]=1;
                backTrace(nums, visited,depth+1, permutation,ans);
            }
            else continue;
            permutation.remove(permutation.size()-1);
            visited[i] = 0;
        }
    }
}

//leetcode submit region end(Prohibit modification and deletion)
