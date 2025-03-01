//给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。 
//
// 返回这三个数的和。 
//
// 假定每组输入只存在恰好一个解。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,0,0], target = 1
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// 3 <= nums.length <= 1000 
// -1000 <= nums[i] <= 1000 
// -10⁴ <= target <= 10⁴ 
// 
//
// Related Topics 数组 双指针 排序 👍 1323 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 双指针+排序
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);// 按从小到大排序
        int ans = 100000;

        for(int i=0;i<n;i++){// 枚举第一个数
            if(i>0&&nums[i]==nums[i-1]){// 跳过重复的数据
                continue;
            }
            int left = i+1;// 左指针
            int right = n-1;// 右指针
            while(left<right){
                int sum = nums[i]+nums[left]+nums[right];
                if(sum==target)return target;
                if (Math.abs(sum - target)< Math.abs(ans - target)) {
                    ans = sum;
                }
                if(sum<target) {// 移动左指针到下一个不重复的数据位置
                    int ltemp = left + 1;
                    while(ltemp<right&&nums[left]==nums[ltemp])ltemp++;
                    left = ltemp;
                }
                else {
                    int rtemp = right - 1;
                    while(rtemp>left&&nums[right]==nums[rtemp])rtemp--;
                    right = rtemp;
                }

            }
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
