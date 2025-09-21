package github.grit.gaia.demo.datastructure;

/**
 * 树状数组（Fenwick Tree / Binary Indexed Tree）
 * 支持单点更新和前缀和查询
 */
public class FenwickTree {
    private final int[] tree;
    private final int n;

    public FenwickTree(int size) {
        this.n = size + 1; // 下标从1开始
        this.tree = new int[n];
    }

    /**
     * lowbit: 取二进制最低位的1
     */
    private int lowbit(int x) {
        return x & (-x);
    }

    /**
     * 在位置 i 上增加 delta（i 从 1 开始）
     */
    public void update(int i, int delta) {
        while (i < n) {
            tree[i] += delta;
            i += lowbit(i);
        }
    }

    /**
     * 查询前缀和 [1, i]
     */
    public int query(int i) {
        int sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= lowbit(i);
        }
        return sum;
    }

    /**
     * 查询区间和 [left, right]（闭区间，下标从1起）
     */
    public int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }

    // 测试
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        FenwickTree ft = new FenwickTree(arr.length);

        // 初始化
        for (int i = 0; i < arr.length; i++) {
            ft.update(i + 1, arr[i]);
        }

        System.out.println("前缀和[1~3]: " + ft.query(3));           // 1+3+5=9
        System.out.println("区间和[2~4]: " + ft.rangeQuery(2, 4));   // 3+5+7=15

        ft.update(3, 2); // 给第3个数加2 → 5→7
        System.out.println("更新后区间和[2~4]: " + ft.rangeQuery(2, 4)); // 3+7+7=17
    }
}
