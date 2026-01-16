package github.grit.gaia.demo.leetcode;

import java.util.*;


/**
 * 1912. 设计电影租借系统
 */
public class _1912_MovieRentingSystem {

    // 存储 (shop, movie) -> price，方便快速查询
    private Map<Long, Integer> priceMap = new HashMap<>();

    // unRentedMovieSet: 每个电影对应一个 TreeSet，保存未租出的商店中的该电影（按价格、shop 排序）
    private Map<Integer, TreeSet<_1912_MovieRentingSystem.Movie>> unRentedMovieSet = new HashMap<>();

    // 已租出的电影，全局 TreeSet，按价格、shop、movie 排序
    private TreeSet<_1912_MovieRentingSystem.Movie> rentedMovieSet = new TreeSet<>((a, b) -> {
        if (a.price != b.price) return Integer.compare(a.price, b.price);
        if (a.shop != b.shop) return Integer.compare(a.shop, b.shop);
        return Integer.compare(a.movie, b.movie);
    });

    // 将 (shop, movie) 映射为唯一 long key（因为 shop < 3e5，可用 20 位表示）
    private long getKey(int shop, int movie) {
        return ((long) shop << 20) | movie;
    }

    static class Movie {
        int shop;
        int movie;
        int price;

        public Movie(int shop, int movie, int price) {
            this.shop = shop;
            this.movie = movie;
            this.price = price;
        }
    }

    public _1912_MovieRentingSystem(int n, int[][] entries) {
        for (int[] e : entries) {
            int shop = e[0], movie = e[1], price = e[2];
            long key = getKey(shop, movie);

            priceMap.put(key, price);

            // 添加到未租出集合中
            unRentedMovieSet.computeIfAbsent(movie, k -> new TreeSet<>((a, b) -> {
                if (a.price != b.price) return Integer.compare(a.price, b.price);
                return Integer.compare(a.shop, b.shop); // 价格相同按 shop 升序
            })).add(new Movie(shop, movie, price));
        }
    }

    public List<Integer> search(int movie) {
        TreeSet<_1912_MovieRentingSystem.Movie> set = unRentedMovieSet.get(movie);
        List<Integer> result = new ArrayList<>();
        if (set == null || set.isEmpty()) return result;

        // 取最多前 5 个
        int count = 0;
        for (_1912_MovieRentingSystem.Movie m : set) {
            if (count >= 5) break;
            result.add(m.shop);
            count++;
        }
        return result;
    }

    public void rent(int shop, int movie) {
        int price = priceMap.get(getKey(shop, movie));
        _1912_MovieRentingSystem.Movie movieObj = new Movie(shop, movie, price);

        // 从未租出集合中移除
        TreeSet<_1912_MovieRentingSystem.Movie> set = unRentedMovieSet.get(movie);
        set.remove(movieObj);

        // 加入已租出集合
        rentedMovieSet.add(movieObj);
    }

    public void drop(int shop, int movie) {
        int price = priceMap.get(getKey(shop, movie));
        _1912_MovieRentingSystem.Movie movieObj = new Movie(shop, movie, price);

        // 从已租出集合中移除
        rentedMovieSet.remove(movieObj);

        // 加回未租出集合
        unRentedMovieSet.get(movie).add(movieObj);

    }

    public List<List<Integer>> report() {
        List<List<Integer>> result = new ArrayList<>();
        int count = 0;
        for (_1912_MovieRentingSystem.Movie m : rentedMovieSet) {
            if (count >= 5) break;
            List<Integer> item = new ArrayList<>();
            item.add(m.shop);
            item.add(m.movie);
            result.add(item);
            count++;
        }
        return result;
    }
}