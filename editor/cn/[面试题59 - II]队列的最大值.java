//请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都
//是O(1)。 
//
// 若队列为空，pop_front 和 max_value 需要返回 -1 
//
// 示例 1： 
//
// 输入: 
//["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
//[[],[1],[2],[],[],[]]
//输出: [null,null,null,2,1,2]
// 
//
// 示例 2： 
//
// 输入: 
//["MaxQueue","pop_front","max_value"]
//[[],[],[]]
//输出: [null,-1,-1]
// 
//
// 
//
// 限制： 
//
// 
// 1 <= push_back,pop_front,max_value的总操作数 <= 10000 
// 1 <= value <= 10^5 
// 
//
// Related Topics 设计 队列 单调队列 👍 463 👎 0


import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class MaxQueue {

    LinkedList<Integer> deque;
    PriorityQueue<Integer> pq;
    public MaxQueue() {
        deque = new LinkedList<>();
        pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
    }
    
    public int max_value() {
        return pq.peek()==null ? -1 : pq.peek();
    }
    
    public void push_back(int value) {
        deque.offer(value);
        pq.offer(value);
    }
    
    public int pop_front() {
        Integer elem = deque.poll();
        pq.remove(elem);
        return elem == null ? -1 : elem.intValue();
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */
//leetcode submit region end(Prohibit modification and deletion)
