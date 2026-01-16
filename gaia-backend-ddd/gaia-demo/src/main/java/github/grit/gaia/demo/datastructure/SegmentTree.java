package github.grit.gaia.demo.datastructure;

/**
 * 线段树（Segment Tree）—— 区间和查询 + 单点更新
 */
public class SegmentTree {
    private final int[] tree; // 存储线段树节点值
    private final int n;

    public SegmentTree(int[] arr) {
        this.n = arr.length;
        this.tree = new int[4 * n]; // 一般开 4 倍空间
        build(arr, 0, 0, n - 1);
    }

    /**
     * 构建线段树
     * node: 当前节点编号
     * start, end: 当前节点表示的区间
     */
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = start + (end - start) / 2;
            int leftNode = 2 * node + 1;
            int rightNode = 2 * node + 2;

            build(arr, leftNode, start, mid);
            build(arr, rightNode, mid + 1, end);
            tree[node] = tree[leftNode] + tree[rightNode];
        }
    }

    /**
     * 更新单点值：arr[index] += delta
     */
    public void update(int index, int delta) {
        update(0, 0, n - 1, index, delta);
    }

    private void update(int node, int start, int end, int idx, int delta) {
        if (start == end) {
            tree[node] += delta;
        } else {
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, delta);
            } else {
                update(2 * node + 2, mid + 1, end, idx, delta);
            }
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    /**
     * 查询区间和 [left, right]
     */
    public int query(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }

    private int query(int node, int start, int end, int L, int R) {
        if (R < start || L > end) return 0;
        if (L <= start && end <= R) return tree[node];

        int mid = start + (end - start) / 2;
        int leftSum = query(2 * node + 1, start, mid, L, R);
        int rightSum = query(2 * node + 2, mid + 1, end, L, R);
        return leftSum + rightSum;
    }

    // 测试
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTree st = new SegmentTree(arr);

        System.out.println("初始[1~3]和: " + st.query(1, 3)); // 3+5+7=15
        st.update(2, 2); // 第2个位置（即 arr[2]=5）加2 → 变成7
        System.out.println("更新后[1~3]和: " + st.query(1, 3)); // 3+7+7=17
    }
}
