//给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。 
//
// 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0]
//输出：[[],[0]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10 
// -10 <= nums[i] <= 10 
// nums 中的所有元素 互不相同 
// 
//
// Related Topics 位运算 数组 回溯 👍 1967 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 回溯(效率低）、位运算
    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> list = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {

        dfs(0,nums);
        return ans;
    }

    public void dfs(int cur, int[] nums){// cur表示当前位置
        if(cur == nums.length ){
            ans.add(new ArrayList<>(list));
            return;
        }

        // 对每个位置的数选择或者不选择
        // 选择
        list.add(nums[cur]);
        dfs(cur+1, nums);
        list.remove(list.size()-1);
        // 不选择
        dfs(cur+1,nums);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
