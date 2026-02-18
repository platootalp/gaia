//请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。 
//
// 
//
// 例如: 给定二叉树: [3,9,20,null,null,15,7], 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其层次遍历结果： 
//
// [
//  [3],
//  [20,9],
//  [15,7]
//]
// 
//
// 
//
// 提示： 
//
// 
// 节点总数 <= 1000 
// 
//
// Related Topics 树 广度优先搜索 二叉树 👍 284 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }*/
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null){
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean rev = false;
        while (!queue.isEmpty()){
            int node_num = queue.size();
            List<Integer> temp = new ArrayList<>();
            //List<Integer> temp = new ArrayList<>(Collections.nCopies(node_num,0));
            for(int i=0;i<node_num;i++){
                TreeNode cur = queue.poll();
                if(cur.left!=null)queue.offer(cur.left);
                if(cur.right!=null)queue.offer(cur.right);
                int j = rev?node_num-i-1:i;
                temp.add(cur.val);
                //temp.set(j,cur.val);
            }
            if(rev){
                Collections.reverse(temp);
            }
            rev = !rev;
            ans.add(temp);
        }
        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
