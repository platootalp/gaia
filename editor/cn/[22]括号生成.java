//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：["((()))","(()())","(())()","()(())","()()()"]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：["()"]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 8 
// 
//
// Related Topics 字符串 动态规划 回溯 👍 3129 👎 0


import java.util.ArrayList;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 回溯+栈
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backTrack(n*2, new StringBuilder(), ans);
        return ans;
    }

    public void backTrack(int n, StringBuilder s, List<String> ans){
        String bracket = "()";
        if(n==0) {
            if(isVaild(s))ans.add(s.toString());
            return;
        }
        else{
            for(int i = 0;  i < 2; i++){
                s.append(bracket.charAt(i));
                backTrack(n-1, s, ans);
                s.deleteCharAt(s.length()-1);
            }
        }
    }

    public boolean isVaild (StringBuilder s){// 判断括号的有效性
        Stack<Character> stack = new Stack<>();
        int len = s.length();
        for(int i = 0 ; i < len ; i++){
            if(s.charAt(i)=='(') {
                stack.push(s.charAt(i));
            }
            else {
                if(stack.empty())return false;
                else stack.pop();
            }
        }

        if(stack.empty())return true;
        else return false;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
