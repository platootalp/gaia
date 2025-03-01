//给定一棵二叉搜索树，请找出其中第 k 大的节点的值。 
//
// 
//
// 示例 1: 
//
// 
//输入: root = [3,1,4,null,2], k = 1
//   3
//  / \
// 1   4
//  \
//   2
//输出: 4 
//
// 示例 2: 
//
// 
//输入: root = [5,3,6,2,4,null,null,1], k = 3
//       5
//      / \
//     3   6
//    / \
//   2   4
//  /
// 1
//输出: 4 
//
// 
//
// 限制： 
//
// 
// 1 ≤ k ≤ 二叉搜索树元素个数 
// 
//
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 381 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.Collections;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }*/
class Solution {

    ArrayList<Integer> list = new ArrayList<>();
    public int kthLargest(TreeNode root, int k) {
        dfs(root);
        return list.get(list.size()-k);
    }
    // 二叉搜索树的中序遍历序列为递增序列
    public void dfs(TreeNode root){
        if(root==null) {
            return;
        }
        dfs(root.left);
        list.add(root.val);
        dfs(root.right);
        return;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
