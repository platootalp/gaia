package github.grit.gaia.demo.datastructure;

/**
 * 字典树（Trie）
 */
public class Trie {
    private final TrieNode root;

    static class TrieNode {
        TrieNode[] children = new TrieNode[26]; // 假设只有小写字母 a-z
        boolean isEnd; // 标记是否是一个完整单词的结尾
    }

    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * 插入一个单词
     */
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    /**
     * 查找单词是否存在
     */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    /**
     * 判断是否有以该前缀开头的单词
     */
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                return null;
            }
            node = node.children[idx];
        }
        return node;
    }

    // 测试
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // true
        System.out.println(trie.search("app"));     // false
        System.out.println(trie.startsWith("app")); // true
        trie.insert("app");
        System.out.println(trie.search("app"));     // true
    }
}