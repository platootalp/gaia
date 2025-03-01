//整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。 
//
// 
// 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。 
// 
//
// 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就
//是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。 
//
// 
// 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。 
// 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。 
// 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。 
// 
//
// 给你一个整数数组 nums ，找出 nums 的下一个排列。 
//
// 必须 原地 修改，只允许使用额外常数空间。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,1]
//输出：[1,2,3]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,1,5]
//输出：[1,5,1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 100 
// 
//
// Related Topics 数组 双指针 👍 2104 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 双指针
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        if(len<2)return;
        int l=len-2,r=len-1;// 左右指针

        while(l>=0){// 找到要交换的较小的数
            if(nums[l]<nums[r]){
                break;
            }
            l--;
            r--;
        }

        if(l>=0){// 当l<0时，说明数组已经为最大排列了，只需要反转即可
            r = len-1;
            while(r>l){// 在较小的数的右边找要交换的较大的数
                if(nums[r]>nums[l]){
                    break;
                }
                r--;
            }
            // 进行交换
            swap(nums,l,r);
        }

        // 对l右边的数进行升序排序（此时为降序序列）
        l=l+1;
        r=len-1;
        while(l<=r && l<len){
            swap(nums,l,r);
            l++;
            r--;
        }

    }

    public void swap(int nums[], int l, int r){
        int temp = nums[l];
        nums[l]=nums[r];
        nums[r]=temp;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
