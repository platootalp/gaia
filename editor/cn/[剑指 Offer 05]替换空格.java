//请实现一个函数，把字符串 s 中的每个空格替换成"%20"。 
//
// 
//
// 示例 1： 
//
// 输入：s = "We are happy."
//输出："We%20are%20happy." 
//
// 
//
// 限制： 
//
// 0 <= s 的长度 <= 10000 
//
// Related Topics 字符串 👍 455 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String replaceSpace(String s) {
        int n = s.length();
        if(n<1){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        int start = 0;
        for(int i=0;i<n;i++){
            if(s.charAt(i)==' '){
                stringBuffer.append(s.substring(start,i)+"%20");
                start = i+1;
            }
        }
        stringBuffer.append(s.substring(start));
        return stringBuffer.toString();
        s.replace();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
