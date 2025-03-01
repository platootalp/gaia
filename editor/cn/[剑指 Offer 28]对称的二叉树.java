//请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。 
//
// 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。 
//
// 1 / \ 2 2 / \ / \ 3 4 4 3 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的: 
//
// 1 / \ 2 2 \ \ 3 3 
//
// 
//
// 示例 1： 
//
// 输入：root = [1,2,2,3,4,4,3]
//输出：true
// 
//
// 示例 2： 
//
// 输入：root = [1,2,2,null,3,null,3]
//输出：false 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 1000 
//
// 注意：本题与主站 101 题相同：https://leetcode-cn.com/problems/symmetric-tree/ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 439 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }*/
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }
        return isSame(root.left,root.right);
    }
    public boolean isSame(TreeNode left, TreeNode right){
        if(left==null && right==null){
            return true;
        }
        if(left==null || right==null){
            return false;
        }
        return left.val==right.val && isSame(left.left,right.right) && isSame(left.right,right.left);
    }
//    public boolean isSymmetric(TreeNode root) {
//        if(root==null){
//            return true;
//        }
//        TreeNode mirrorRoot = mirrorTree(root);
//        return isSame(root,mirrorRoot);
//    }
//    public boolean isSame(TreeNode root, TreeNode mirrorRoot){
//        if(root==null && mirrorRoot==null){
//            return true;
//        }
//        if(root==null || mirrorRoot==null){
//            return false;
//        }
//        return root.val==mirrorRoot.val && isSame(root.left,mirrorRoot.left) && isSame(root.right,mirrorRoot.right);
//    }
//    public TreeNode mirrorTree(TreeNode root) {
//        if(root==null){
//            return null;
//        }
//        TreeNode newNode  = new TreeNode(root.val);
//        newNode.left = mirrorTree(root.right);
//        newNode.right = mirrorTree(root.left);
//        return newNode;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
