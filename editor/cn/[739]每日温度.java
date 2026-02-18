//给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现
//在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。 
//
// 
//
// 示例 1: 
//
// 
//输入: temperatures = [73,74,75,71,69,72,76,73]
//输出: [1,1,4,2,1,1,0,0]
// 
//
// 示例 2: 
//
// 
//输入: temperatures = [30,40,50,60]
//输出: [1,1,1,0]
// 
//
// 示例 3: 
//
// 
//输入: temperatures = [30,60,90]
//输出: [1,1,0] 
//
// 
//
// 提示： 
//
// 
// 1 <= temperatures.length <= 10⁵ 
// 30 <= temperatures[i] <= 100 
// 
//
// Related Topics 栈 数组 单调栈 👍 1456 👎 0


import java.util.Deque;
import java.util.LinkedList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        Deque<Integer> stack = new LinkedList<>();
        Deque<Integer> idxStack = new LinkedList<>();
        stack.push(101);
        idxStack.push(-1);

        int[] ans = new int[n];
        for(int i=0;i<n;i++){
            if(temperatures[i]<stack.peek()){
                stack.push(temperatures[i]);
                idxStack.push(i);
            }
            while(temperatures[i]>stack.peek()){
                stack.pop();
                int idx = idxStack.pop();
                ans[idx] = i-idx;
            }
            stack.push(temperatures[i]);
            idxStack.push(i);
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
