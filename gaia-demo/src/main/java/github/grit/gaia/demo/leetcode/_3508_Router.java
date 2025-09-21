package github.grit.gaia.demo.leetcode;

import java.util.*;


/**
 * 3508. 设计路由器
 */
class _3508_Router {
    private Set<String> md5Set;
    private Deque<Packet> packetDeque;
    private int memoryLimit;
    private Map<Integer, List<Integer>> destToTimestamps; // 存所有 timestamp，不去重

    static class Packet {
        int source;
        int destination;
        int timestamp;

        public Packet(int source, int destination, int timestamp) {
            this.source = source;
            this.destination = destination;
            this.timestamp = timestamp;
        }
    }

    public _3508_Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        this.packetDeque = new LinkedList<>();
        this.md5Set = new HashSet<>();
        this.destToTimestamps = new HashMap<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        String md5 = source + "-" + destination + "-" + timestamp;
        if (md5Set.contains(md5)) {
            return false;
        }

        md5Set.add(md5);

        if (packetDeque.size() == memoryLimit) {
            Packet removed = packetDeque.pop();
            cleanupPacket(removed);
        }

        Packet packet = new Packet(source, destination, timestamp);
        packetDeque.add(packet);

        // 添加到 list，暂不排序
        destToTimestamps.computeIfAbsent(destination, k -> new ArrayList<>()).add(timestamp);

        return true;
    }

    public int[] forwardPacket() {
        if (packetDeque.isEmpty()) {
            return new int[]{};
        }

        Packet packet = packetDeque.pop();
        cleanupPacket(packet);

        return new int[]{packet.source, packet.destination, packet.timestamp};
    }

    private void cleanupPacket(Packet packet) {
        String md5 = packet.source + "-" + packet.destination + "-" + packet.timestamp;
        md5Set.remove(md5);

        List<Integer> list = destToTimestamps.get(packet.destination);
        if (list != null) {
            // 从 list 中移除一个等于 packet.timestamp 的元素（从后往前删更安全）
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i).equals(packet.timestamp)) {
                    list.remove(i);
                    break;
                }
            }
            if (list.isEmpty()) {
                destToTimestamps.remove(packet.destination);
            }
        }
    }

    public int getCount(int destination, int startTime, int endTime) {
        List<Integer> list = destToTimestamps.getOrDefault(destination, Collections.emptyList());
        if (list.isEmpty()) return 0;

        int left = lowerBound(list, startTime);   // first index >= startTime
        int right = upperBound(list, endTime);    // first index > endTime

        return right - left;
    }

    private int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int upperBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
