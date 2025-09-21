package github.grit.gaia.demo.datastructure;

/**
 * 并查集（Union-Find / Disjoint Set Union, DSU）
 */
public class UnionFind {
    private final int[] parent;
    private final int[] rank;
    private int count;

    public UnionFind(int n) {
        this.count = n;
        this.parent = new int[n];
        this.rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 查找根节点（带路径压缩）
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // 路径压缩
        }
        return parent[x];
    }

    /**
     * 合并两个集合（按秩合并）
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        count--;
    }

    /**
     * 是否连通
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    /**
     * 获取连通分量数量
     */
    public int getCount() {
        return count;
    }

    /**
     * 获取某个元素所在集合的大小（需额外维护 size 数组才准确）
     * 这里只是返回根节点的 rank（近似值）
     */
    public int getSize(int x) {
        int root = find(x);
        return rank[root]; // 注意：这不是精确大小，仅作参考
    }

    // 测试
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10); // 创建 10 个节点：0 ~ 9

        // 构建第一个连通分量：0-1-2
        uf.union(0, 1);
        uf.union(1, 2);

        // 第二个连通分量：3-4
        uf.union(3, 4);

        // 第三个连通分量：5-6-7-8-9
        uf.union(5, 6);
        uf.union(6, 7);
        uf.union(7, 8);
        uf.union(8, 9);

        System.out.println("0 和 2 连通: " + uf.connected(0, 2));     // true
        System.out.println("0 和 9 连通: " + uf.connected(0, 9));     // false
        System.out.println("连通分量个数: " + uf.getCount());          // 应该是 3

        // 再连接一下试试
        uf.union(2, 3); // 把 0-1-2 和 3-4 连起来 → 现在 0,1,2,3,4 连通
        System.out.println("0 和 4 连通: " + uf.connected(0, 4));     // true
        System.out.println("连通分量个数: " + uf.getCount());          // 应该是 2
    }
}