//输入一个字符串，打印出该字符串中字符的所有排列。 
//
// 
//
// 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。 
//
// 
//
// 示例: 
//
// 输入：s = "abc"
//输出：["abc","acb","bac","bca","cab","cba"]
// 
//
// 
//
// 限制： 
//
// 1 <= s 的长度 <= 8 
//
// Related Topics 字符串 回溯 👍 677 👎 0


import java.util.ArrayList;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    ArrayList<String> list = new ArrayList<>();
    boolean[] visited;
    public String[] permutation(String s) {
        int n = s.length();
        visited = new boolean[n];
        backTrace(s,0, new StringBuilder());
        // 去重
        HashSet<String> set = new HashSet<>();
        for(int i=0;i<list.size();i++){
            if(!set.contains(list.get(i))){
                set.add(list.get(i));
            }
        }
        String[] ans = new String[set.size()];
        int index =0;
        for (String s1 : set) {
            ans[index++] = s1;
        }
        return ans;
    }
    public void backTrace(String s, int len,StringBuilder temp) {
        if (len == s.length()) {
            list.add(new String(temp));
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                temp.append(s.charAt(i));
                backTrace(s, len + 1, temp);
                temp.deleteCharAt(temp.length()-1);
                visited[i] = false;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
