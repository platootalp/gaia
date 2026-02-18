//给你一个二叉树的根节点 root ， 检查它是否轴对称。 
//
// 
//
// 示例 1： 
// 
// 
//输入：root = [1,2,2,3,4,4,3]
//输出：true
// 
//
// 示例 2： 
// 
// 
//输入：root = [1,2,2,null,3,null,3]
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [1, 1000] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你可以运用递归和迭代两种方法解决这个问题吗？ 
//
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 2335 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
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
    // 递归、栈
    public boolean isSymmetric(TreeNode root){
        return check(root,root);
    }
    public boolean check(TreeNode p, TreeNode q){
        if(p==null && q==null){
            return true;
        }
        if(p==null || q==null){
            return false;
        }
        return p.val== q.val && check(p.left,q.right) && check(p.right,q.left);
    }
// 有问题
//    public boolean isSymmetric(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        traversal(root,list);
//        int l=0, r = list.size()-1;
//        while(l<r){
//            if(list.get(l)!=list.get(r))break;
//            l++;
//            r--;
//        }
//
//        return l==r ? true : false;
//    }
//    public void traversal(TreeNode t, List<Integer> list){
//        // 中序遍历
//        if(t==null){
//            list.add(-101);
//            return;
//        }
//        traversal(t.left, list);
//        list.add(t.val);
//        traversal(t.right,list);
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
