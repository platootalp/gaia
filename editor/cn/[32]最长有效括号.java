//给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。 
//
// 
//
// 
// 
// 示例 1： 
// 
// 
//
// 
//输入：s = "(()"
//输出：2
//解释：最长有效括号子串是 "()"
// 
//
// 示例 2： 
//
// 
//输入：s = ")()())"
//输出：4
//解释：最长有效括号子串是 "()()"
// 
//
// 示例 3： 
//
// 
//输入：s = ""
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 3 * 10⁴ 
// s[i] 为 '(' 或 ')' 
// 
//
// Related Topics 栈 字符串 动态规划 👍 2187 👎 0


import java.util.Deque;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 栈或动态规划
    public int longestValidParentheses(String s) {
        int len = s.length();
        if(len<2)return 0;
        int ans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);// 用于记录最后一个未被匹配到的右括号
        for(int i = 0; i<len;i++){
            if(s.charAt(i)=='('){// 左括号直接将下标压栈
                stack.push(i);
            }
            else{
                stack.pop();
                if(stack.empty()){// 当栈为空时说明当前右括号没有匹配到左括号
                    stack.push(i);
                }
                else{
                    // 当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
                    ans = Math.max(ans,i-stack.peek());
                }
            }
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
