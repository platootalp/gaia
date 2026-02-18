//给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 5 * 10⁴ 
// s 由英文字母、数字、符号和空格组成 
// 
//
// Related Topics 哈希表 字符串 滑动窗口 👍 8642 👎 0


import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // hash+滑动窗口
    public int lengthOfLongestSubstring(String s) {
        int sum = 0;//最长子串长度
        int left = 0;//子串左指针
        int right = 0;//子串右指针
        HashSet<Character> set = new HashSet<>();

        while(left<s.length()){
            while(right<s.length()&&!set.contains(s.charAt(right))){
                set.add(s.charAt(right));
                right++;
            }
            sum=Math.max(right-left,sum);
            set.remove(s.charAt(left));
            left++;
        }
        if(s.length()==1)return 1;
        else return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
