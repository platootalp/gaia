//定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。 
//
// 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 5000 
//
// 
//
// 注意：本题与主站 206 题相同：https://leetcode-cn.com/problems/reverse-linked-list/ 
//
// Related Topics 递归 链表 👍 569 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        // 迭代
        // 1. 定义三个指针
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;
        // 2. 迭代过程
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
//    public ListNode reverseList(ListNode head) {
//        // 递归
//        // 1. 递归终止条件
//        if (head == null || head.next == null) {
//            return head;
//        }
//        // 2. 递归过程
//        ListNode newHead = reverseList(head.next);
//        head.next.next = head;
//        head.next = null;
//        return newHead;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
