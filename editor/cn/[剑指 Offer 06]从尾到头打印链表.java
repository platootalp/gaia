//输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。 
//
// 
//
// 示例 1： 
//
// 输入：head = [1,3,2]
//输出：[2,3,1] 
//
// 
//
// 限制： 
//
// 0 <= 链表长度 <= 10000 
//
// Related Topics 栈 递归 链表 双指针 👍 407 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        if(head == null){
            return new int[]{};
        }
        List<Integer> list = new ArrayList<>();
        dfs(head,list);
        int[] ans = new int[list.size()];
        for(int i=0;i<list.size();i++){
            ans[i] = list.get(i);
        }
        return ans;
    }
    public void dfs(ListNode head, List<Integer> list){
        if(head==null){
            return;
        }
        dfs(head.next,list);
        list.add(head.val);
        return;
    }
//    public int[] reversePrint(ListNode head) {
//        Deque<Integer> stack = new LinkedList<>();
//        while(head!=null){
//            stack.push(head.val);
//            head = head.next;
//        }
//        int[] ans = new int[stack.size()];
//        int i = 0;
//        while(!stack.isEmpty()){
//            ans[i] = stack.pop();
//            i++;
//        }
//        return ans;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
