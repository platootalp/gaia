//整数数组 nums 按升序排列，数组中的值 互不相同 。 
//
// 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[
//k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2
//,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。 
//
// 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。 
//
// 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [4,5,6,7,0,1,2], target = 0
//输出：4
// 
//
// 示例 2： 
//
// 
//输入：nums = [4,5,6,7,0,1,2], target = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：nums = [1], target = 0
//输出：-1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 5000 
// -10⁴ <= nums[i] <= 10⁴ 
// nums 中的每个值都 独一无二 
// 题目数据保证 nums 在预先未知的某个下标上进行了旋转 
// -10⁴ <= target <= 10⁴ 
// 
//
// Related Topics 数组 二分查找 👍 2534 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 二分查找
    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }
        if (len == 1) {
            return nums[0] == target ? 0 : -1;
        }

        int index = 0;
        while(index+1<len){// 找到旋转的位置
            if(nums[index]>nums[index+1])break;
            index += 1;
        }

        return binarySearch(nums, 0, index, target)!=-1 ?
                binarySearch(nums, 0, index, target) :
                binarySearch(nums, index+1, len-1, target);

    }

    public int binarySearch(int[] nums, int l, int r, int target){// 二分查找

        if(l>r)return -1;
        int mid = (l+r)/2;
        while(l<=r){
            if(nums[mid]>target){
                r = mid-1;
                mid = (l+r)/2;
            }
            else if(nums[mid]<target){
                l = mid+1;
                mid = (l+r)/2;
            }
            else {
                return mid;
            }
        }

        return -1;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
