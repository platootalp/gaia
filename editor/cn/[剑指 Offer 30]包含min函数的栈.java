//定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。 
//
// 
//
// 示例: 
//
// MinStack minStack = new MinStack();
//minStack.push(-2);
//minStack.push(0);
//minStack.push(-3);
//minStack.min();   --> 返回 -3.
//minStack.pop();
//minStack.top();      --> 返回 0.
//minStack.min();   --> 返回 -2.
// 
//
// 
//
// 提示： 
//
// 
// 各函数的调用总次数不超过 20000 次 
// 
//
// 
//
// 注意：本题与主站 155 题相同：https://leetcode-cn.com/problems/min-stack/ 
//
// Related Topics 栈 设计 👍 487 👎 0


import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

//leetcode submit region begin(Prohibit modification and deletion)
class MinStack {

    /** initialize your data structure here. */
    Stack<Long> stack;
    Long min;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int x) {
        if (stack.isEmpty()) {
            stack.push(0l);
            min = Long.valueOf(x);
        } else {
            stack.push((long) (x - min));
            min = Math.min(x, min);
        }
    }

    public void pop() {
        if (stack.peek() < 0) {
            min = min - stack.pop();
            return;
        }
        stack.pop();
    }

    public int top() {
        if (stack.peek() > 0) {
            return (int) (min + stack.peek());
        } else {
            return Math.toIntExact(min);
        }
    }

    public int min() {
        return Math.toIntExact(min);
    }
//    Deque<Integer> stack;
//    PriorityQueue<Integer> pq;
//    public MinStack() {
//       stack = new LinkedList<>();
//       pq = new PriorityQueue<>();
//    }
//
//    public void push(int x) {
//        stack.push(x);
//        pq.offer(x);
//    }
//
//    public void pop() {
//        int elem = stack.pop();
//        pq.remove(elem);
//    }
//
//    public int top() {
//       return stack.peek();
//    }
//
//    public int min() {
//        return pq.peek();
//    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
//leetcode submit region end(Prohibit modification and deletion)
