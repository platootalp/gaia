//小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为
// root 。 
//
// 除了
// root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的
//房子在同一天晚上被打劫 ，房屋将自动报警。 
//
// 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: root = [3,2,3,null,3,null,1]
//输出: 7 
//解释: 小偷一晚能够盗取的最高金额 3 + 3 + 1 = 7 
//
// 示例 2: 
//
// 
//
// 
//输入: root = [3,4,5,1,3,null,1]
//输出: 9
//解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
// 
//
// 
//
// 提示： 
//
// 
// 
//
// 
// 树的节点数在 [1, 10⁴] 范围内 
// 0 <= Node.val <= 10⁴ 
// 
//
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 1613 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.HashMap;
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }*/
class Solution {

    HashMap<TreeNode,Integer> f = new HashMap<>();// 选中当前结点所得到的金额的最大值
    HashMap<TreeNode,Integer> g = new HashMap<>();// 未选中当前结点所得到的金额的最大值

    public int rob(TreeNode root) {
        dfs(root);
        return Math.max(f.getOrDefault(root,0),g.getOrDefault(root,0));
    }
    public void dfs(TreeNode node){
        if(node == null){
            return;
        }
        if(node.left==null&&node.right==null){// 叶子结点
            f.put(node,node.val);
            g.put(node,0);
            return;
        }
        dfs(node.left);
        dfs(node.right);
        f.put(node,node.val+g.getOrDefault(node.left,0)+g.getOrDefault(node.right,0));
        g.put(node,Math.max(f.getOrDefault(node.left,0),g.getOrDefault(node.left,0))
                    + Math.max(f.getOrDefault(node.right,0),g.getOrDefault(node.right,0)));
        return;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
