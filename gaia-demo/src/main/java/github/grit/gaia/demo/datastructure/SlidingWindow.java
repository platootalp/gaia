package github.grit.gaia.demo.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SlidingWindow<T> {
	protected int left = 0;
	protected int right = 0;

	// 每次加入一个元素（右边界扩张）
	protected abstract void add(T element);

	// 每次移除一个元素（左边界收缩）
	protected abstract void remove(T element);

	// 当前窗口是否满足特定条件
	protected abstract boolean isValid();

	// 当前窗口处理逻辑（记录答案等）
	protected abstract void process();

	// 执行滑动窗口
	public void slidingWindow(List<T> data, int windowSize) {
		for (right = 0; right < data.size(); right++) {
			// in
			add(data.get(right));

			// 窗口扩张
			if(right < windowSize - 1){
				continue;
			}

			// update 数据
			if (isValid()) {
				process();
			}

			// out
			remove(data.get(left));
			left++;
		}
	}

	static class SubstringFrequencyCounter extends SlidingWindow<Character> {
		private final int maxLetters;
		private final int windowSize;
		private final Map<Character, Integer> charCountMap = new HashMap<>();
		private final StringBuilder windowStr = new StringBuilder();
		private final Map<String, Integer> freqMap = new HashMap<>();
		private int maxFreq = 0;

		public SubstringFrequencyCounter(int maxLetters, int windowSize) {
			this.maxLetters = maxLetters;
			this.windowSize = windowSize;
		}

		public int getMaxFreq() {
			return maxFreq;
		}

		@Override
		protected void add(Character c) {
			charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
			windowStr.append(c);
		}

		@Override
		protected void remove(Character c) {
			charCountMap.put(c, charCountMap.get(c) - 1);
			if (charCountMap.get(c) == 0) {
				charCountMap.remove(c);
			}
			windowStr.deleteCharAt(0);
		}

		@Override
		protected boolean isValid() {
			return charCountMap.size() <= maxLetters;
		}

		@Override
		protected void process() {
			String subStr = windowStr.toString();
			int count = freqMap.getOrDefault(subStr, 0) + 1;
			freqMap.put(subStr, count);
			maxFreq = Math.max(maxFreq, count);
		}
	}

	public static int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
		int result = 0;
		List<Character> data = s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		for (int size = minSize; size <= maxSize; size++) {
			SubstringFrequencyCounter counter = new SubstringFrequencyCounter(maxLetters, size);
			counter.slidingWindow(data, size);
			result = Math.max(result, counter.getMaxFreq());
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(maxFreq("aababcaab", 2, 3, 4));  // 输出：2
	}
}

