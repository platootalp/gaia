//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。 
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
// 
//
// 示例 2： 
//
// 
//输入：digits = ""
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：digits = "2"
//输出：["a","b","c"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= digits.length <= 4 
// digits[i] 是范围 ['2', '9'] 的一个数字。 
// 
//
// Related Topics 哈希表 字符串 回溯 👍 2289 👎 0


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 回溯+hash
    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<>();
        Map<Character,String> map = new HashMap<>(){{
            put('2',"abc");
            put('3',"def");
            put('4',"ghi");
            put('5',"jkl");
            put('6',"mno");
            put('7',"pqrs");
            put('8',"tuv");
            put('9',"wxyz");
        }};
        if(digits.length()==0)return combinations;
        // 回溯产生所有情况
        backTrace(digits,map,combinations,new StringBuffer(),0);
        return combinations;
    }
    private void backTrace(String digits, Map<Character,String> map, List<String> combinations, StringBuffer combination, int index){
        if(index==digits.length()){
            combinations.add(combination.toString());
        }
        else{
            char digit = digits.charAt(index);
            String letters = map.get(digit);
            int lettersCount = letters.length();
            for(int i = 0; i<lettersCount; i++){
                combination.append(letters.charAt(i));
                backTrace(digits, map, combinations, combination, index+1);
                combination.deleteCharAt(index);
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
