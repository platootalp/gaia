package github.grit.gaia.demo.leetcode;

import java.util.*;

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

    public static void main(String[] args) {

    }
}
