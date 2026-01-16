package github.grit.gaia.demo.algo;

import java.util.Arrays;

/**
 * 二分查找工具类（要求输入数组已按升序排序）
 * <p>
 * 核心思想：仅实现两个基本操作：
 * - lowerBound: 第一个 >= target 的位置
 * - upperBound: 第一个 > target 的位置
 * 其余所有变体均可由这两个方法推导得出。
 */
public class BinarySearch {

    // ==================== 核心方法（必须独立实现） ====================

    /**
     * 查找第一个 >= target 的位置（即 lower_bound）
     *
     * @param arr    升序整型数组
     * @param target 目标值（使用 long 避免比较时溢出）
     * @return 满足 arr[i] >= target 的最小下标 i；
     * 若所有元素都 < target，则返回 arr.length
     */
    public static int lowerBound(int[] arr, long target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] >= target) {
                right = mid - 1; // 条件满足，尝试往左找更小的下标
            } else {
                left = mid + 1;  // 条件不满足，往右找
            }
        }
        return left; // 循环结束时 left = right + 1，指向第一个满足条件的位置
    }

    /**
     * 查找第一个 > target 的位置（即 upper_bound）
     *
     * @param arr    升序整型数组
     * @param target 目标值（使用 long 避免比较时溢出）
     * @return 满足 arr[i] > target 的最小下标 i；
     * 若所有元素都 <= target，则返回 arr.length
     */
    public static int upperBound(int[] arr, long target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                right = mid - 1; // 条件满足，尝试往左找更小的下标
            } else {
                left = mid + 1;  // 条件不满足，往右找
            }
        }
        return left;
    }

    // ==================== 衍生方法（基于核心方法） ====================

    /**
     * 查找第一个等于 target 的位置（首次出现）
     *
     * @param arr    升序数组
     * @param target 目标值
     * @return 第一个等于 target 的下标；若不存在，返回 -1
     */
    public static int firstEqual(int[] arr, int target) {
        int idx = lowerBound(arr, target);
        // 检查该位置是否存在且值确实等于 target
        if (idx < arr.length && arr[idx] == target) {
            return idx;
        }
        return -1;
    }

    /**
     * 查找最后一个等于 target 的位置（最后一次出现）
     *
     * @param arr    升序数组
     * @param target 目标值
     * @return 最后一个等于 target 的下标；若不存在，返回 -1
     */
    public static int lastEqual(int[] arr, int target) {
        int idx = upperBound(arr, target) - 1;
        // 检查该位置是否有效且值等于 target
        if (idx >= 0 && arr[idx] == target) {
            return idx;
        }
        return -1;
    }

    /**
     * 查找最后一个 <= target 的位置
     *
     * @param arr    升序数组
     * @param target 目标值
     * @return 最后一个满足 arr[i] <= target 的下标；
     * 若所有元素都 > target，则返回 -1
     */
    public static int lastLessEqual(int[] arr, long target) {
        return upperBound(arr, target) - 1;
        // 无需额外判断，因为 upperBound 返回 [0, n]，减1后为 [-1, n-1]
    }

    /**
     * 查找最后一个 < target 的位置
     *
     * @param arr    升序数组
     * @param target 目标值
     * @return 最后一个满足 arr[i] < target 的下标；
     * 若所有元素都 >= target，则返回 -1
     */
    public static int lastLess(int[] arr, long target) {
        return lowerBound(arr, target) - 1;
    }

    // ==================== 测试主函数 ====================
    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 2, 3, 4, 5};
        System.out.println("测试数组: " + Arrays.toString(arr));
        System.out.println();

        // --- 测试 target = 2（中间值，有重复）---
        int target = 2;
        System.out.println(">>> target = " + target);
        System.out.println("lowerBound (≥):        " + lowerBound(arr, target));        // 1
        System.out.println("upperBound (>):        " + upperBound(arr, target));        // 4
        System.out.println("firstEqual (== 首):    " + firstEqual(arr, target));        // 1
        System.out.println("lastEqual (== 末):     " + lastEqual(arr, target));         // 3
        System.out.println("lastLessEqual (≤):     " + lastLessEqual(arr, target));     // 3
        System.out.println("lastLess (<):          " + lastLess(arr, target));          // 0
        System.out.println();

        // --- 测试 target = 0（小于所有元素）---
        target = 0;
        System.out.println(">>> target = " + target + "（小于所有元素）");
        System.out.println("lowerBound (≥):        " + lowerBound(arr, target));        // 0
        System.out.println("upperBound (>):        " + upperBound(arr, target));        // 0
        System.out.println("firstEqual (== 首):    " + firstEqual(arr, target));        // -1
        System.out.println("lastEqual (== 末):     " + lastEqual(arr, target));         // -1
        System.out.println("lastLessEqual (≤):     " + lastLessEqual(arr, target));     // -1
        System.out.println("lastLess (<):          " + lastLess(arr, target));          // -1
        System.out.println();

        // --- 测试 target = 6（大于所有元素）---
        target = 6;
        System.out.println(">>> target = " + target + "（大于所有元素）");
        System.out.println("lowerBound (≥):        " + lowerBound(arr, target));        // 7
        System.out.println("upperBound (>):        " + upperBound(arr, target));        // 7
        System.out.println("firstEqual (== 首):    " + firstEqual(arr, target));        // -1
        System.out.println("lastEqual (== 末):     " + lastEqual(arr, target));         // -1
        System.out.println("lastLessEqual (≤):     " + lastLessEqual(arr, target));     // 6
        System.out.println("lastLess (<):          " + lastLess(arr, target));          // 6
        System.out.println();

        // --- 测试 target = 3（唯一值）---
        target = 3;
        System.out.println(">>> target = " + target + "（唯一值）");
        System.out.println("firstEqual == lastEqual: " +
                (firstEqual(arr, target) == lastEqual(arr, target))); // true
        System.out.println("firstEqual: " + firstEqual(arr, target)); // 4
        System.out.println();
    }
}