package github.grit.gaia.demo.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Solution {
    // 812. 最大三角形面积
    public double largestTriangleArea(int[][] points) {
        double res = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    int x1 = points[i][0], y1 = points[i][1];
                    int x2 = points[j][0], y2 = points[j][1];
                    int x3 = points[k][0], y3 = points[k][1];
                    res = Math.max(res, 0.5 * Math.abs(
                            (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1))
                    );
                }
            }
        }
        return res;
    }

    // 3689. 最大子数组总值 I
    public long maxTotalValue(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        return (long) (max - min) * k;
    }

    // 976. 三角形的最大周长
    public int largestPerimeter(int[] nums) {
        if (nums.length < 3) {
            return 0;
        }
        // 贪心一下
        int result = 0;
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= nums.length - 3; i--) {
            int a = nums[i - 2];
            int b = nums[i - 1];
            int c = nums[i];
            if (a + b > c) {
                result = Math.max(result, a + b + c);
            }
        }
        return result;
    }

    // 3100. 换水问题 II
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        int result = numBottles;
        int empty = numBottles;
        while (empty >= numExchange) {
            // 换
            empty -= numExchange;
            // 喝
            result++;
            empty++;
            numExchange++;
        }
        return result;
    }

    // 3698. 分割数组得到最小绝对差
    public long splitArray(int[] nums) {
        int n = nums.length;
        // 最长严格递增前缀
        long pre = nums[0];
        int i = 1;
        while (i < n && nums[i] > nums[i - 1]) {
            pre += nums[i];
            i++;
        }

        // 最长严格递减后缀
        long suf = nums[n - 1];
        int j = n - 2;
        while (j >= 0 && nums[j] > nums[j + 1]) {
            suf += nums[j];
            j--;
        }

        // 情况一
        if (i <= j) {
            return -1;
        }

        long d = pre - suf;
        // 情况二
        if (i - 1 == j) {
            return Math.abs(d);
        }

        // 情况三，suf 多算了一个 nums[i-1]，或者 pre 多算了一个 nums[i-1]
        return Math.min(Math.abs(d + nums[i - 1]), Math.abs(d - nums[i - 1]));
    }

    // 2300. 咒语和药水的成功对数
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length;

        //
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = potions.length - binarySearch(potions, spells[i], success);
        }
        return ans;
    }

    private int binarySearch(int[] potions, int spell, long success) {
        int left = 0;
        int right = potions.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) spell * potions[mid] >= success) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {

    }
}
