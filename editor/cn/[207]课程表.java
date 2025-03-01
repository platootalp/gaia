//你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。 
//
// 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表
//示如果要学习课程 ai 则 必须 先学习课程 bi 。 
//
// 
// 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。 
// 
//
// 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。 
//
// 
//
// 示例 1： 
//
// 
//输入：numCourses = 2, prerequisites = [[1,0]]
//输出：true
//解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。 
//
// 示例 2： 
//
// 
//输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
//输出：false
//解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。 
//
// 
//
// 提示： 
//
// 
// 1 <= numCourses <= 10⁵ 
// 0 <= prerequisites.length <= 5000 
// prerequisites[i].length == 2 
// 0 <= ai, bi < numCourses 
// prerequisites[i] 中的所有课程对 互不相同 
// 
//
// Related Topics 深度优先搜索 广度优先搜索 图 拓扑排序 👍 1544 👎 0


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    // 拓扑排序、dfs、bfs
   List<List<Integer>> edge = new ArrayList<>();// 储存边
    int[] visited;
    boolean isVaild = true;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 储存顶点的状态(0:未搜索，1:搜索中，2:已搜索)
        visited = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            edge.add(new ArrayList<>());
        }
        // 获取边的状态
        for (int[] pre : prerequisites) {
            edge.get(pre[1]).add(pre[0]);
        }
        // 对每个顶点进行dfs
        for(int i=0;i<numCourses && isVaild;i++){
            visited[i]=1;
            dfs(i);
        }
        return isVaild;
    }
    public void dfs(int u){
        // 对当前顶点的每个邻接点进行搜索
        for (Integer v : edge.get(u)) {
            if(visited[v]==1){
               isVaild = false;
               return;
            }
            else if(visited[v]==0){
                visited[v] = 1;
                dfs(v);
            }
        }
        visited[u] = 2;
        return;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
