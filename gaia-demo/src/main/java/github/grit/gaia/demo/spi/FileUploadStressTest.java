package github.grit.gaia.demo.spi;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件上传持续压测工具 (Java + OkHttp + Guava RateLimiter)
 * 功能对标 Python 脚本，支持按时间、QPS 进行压测，并生成详细报告。
 */
public class FileUploadStressTest {

	// ================== 配置区 ==================
	private static final String UPLOAD_URL = "https://file-upload-gateway.tuhu.cn/file-upload/test/v3/file/upload/stream";

	private static final Map<String, String> HEADERS = Map.of(
			"Accept", "*/*",
			"Connection", "keep-alive",
			"auth-type", "boss",
			"Authorization", "Bearer eyJ4NXQiOiJoR3lxRWItLWVBRWExMWtpYWRnOGVwaUhlNFUiLCJraWQiOiJoR3lxRWItLWVBRWExMWtpYWRnOGVwaUhlNFUiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIwOGRjYTIzYjY0NGQ0MWY0ODZiMWRhZDg0OWQ2Y2JiOCIsImFtciI6WyJFbXBsb3llZSJdLCJpc3MiOiJodHRwczovL3Nzby50dWh1LmNuIiwiY2xpZW50X2lkIjoiZXh0LXdlYnNpdGUtYXJjaC1kZXZlbG9wZXItcG9ydGFsIiwiYXVkIjpbImh0dHBzOi8vc3NvLnR1aHUuY24vcmVzb3VyY2VzIiwieWV3dWFwaSJdLCJkZXQiOiI5bUp6RDVNbHlORFlFS2RNaVktMzhRPT0iLCJuYmYiOjE3NTgxMTI2NjEsImlkcCI6IkVtcGxveWVlIiwic2NvcGUiOlsib3BlbmlkIiwib2ZmbGluZV9hY2Nlc3MiLCJ5ZXd1YXBpIl0sImF1dGhfdGltZSI6MTc1ODExMjY2MCwiYWNjb3VudF9uYW1lIjoibGlqdW55aTMiLCJlbXBsb3llZV9pZCI6IjAzOTMyMCIsIm5hbWUiOiLmnY7kv4rkuYkiLCJwaG9uZV9udW1iZXIiOiIxODgxNzA2NTEzOCIsImV4cCI6MTc1ODExNjI2MSwiaWF0IjoxNzU4MTEyNjYxLCJlbWFpbCI6ImxpanVueWkzQHR1aHUuY24ifQ.ixPDaiJtDXFQ08lK7olWjmGq5XhTVrt5wENXOjQxt0ggZ47FpH1QRKdXhGoHHOANWmz5jkNyIBbypVRtfbnQqBpsm9JpUPgeyXf0HttmsGRqVJCxkYZQY7CS6Ygg6cESMCJLeqpXonw1Ew3BFzTgSpuAndFrku-eB6AnigEiWBJfgAwfOgbBofkeZTHq8PbmpBg_SYfJaPNrbAfduvL3eKJXXjGmOg-jAKJdLFv5sthhIk4JC0-NsCZd5fratGRQ5T1KYah-TcTW1xntJJ1tiUEV4QyaOyVQkEhFdd9nSPEn3iwcFd2YEVJBbExNekPqlsb5H5GnqHIIVxGFom3OMQ",
			"orion_biz_fileUpload_appKey", "ext-spring-md-shop-common-api",
			"type", "3",
			"extension", ".mp3",
			"User-Agent", "Apifox/1.0.0 (https://apifox.com)",
			"Host", "file-upload-gateway.tuhu.cn"
	);

	private static final int DEFAULT_DURATION = 180; // 5分钟
	private static final int DEFAULT_QPS = 100;
	private static final String DEFAULT_FILE_NAME = "100k.mp3";

	// =============================================

	private static final String SCRIPT_DIR = System.getProperty("user.dir");
	private static final Path INPUT_DIR = Paths.get(SCRIPT_DIR, "..", "input").toAbsolutePath().normalize();
	private static final Path OUTPUT_DIR = Paths.get(SCRIPT_DIR, "..", "output").toAbsolutePath().normalize();

	private static final Map<String, String> TYPE_MAP = Map.ofEntries(
			Map.entry("jpg", "img"), Map.entry("jpeg", "img"), Map.entry("png", "img"), Map.entry("gif", "img"),
			Map.entry("pdf", "doc"), Map.entry("doc", "doc"), Map.entry("docx", "doc"), Map.entry("txt", "doc"),
			Map.entry("mp4", "video"), Map.entry("avi", "video"), Map.entry("mov", "video"), Map.entry("mkv", "video"),
			Map.entry("mp3", "audio"), Map.entry("wav", "audio"), Map.entry("flac", "audio"), Map.entry("aac", "audio")
	);

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	// OkHttp 客户端 (线程安全，可复用)
	private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
			.connectTimeout(30, TimeUnit.SECONDS)
			.readTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.build();

	// 全局结果存储（线程安全）
	private static final List<UploadResult> ALL_RESULTS = Collections.synchronizedList(new ArrayList<>());
	private static final Object LOCK = new Object();

	public static void main(String[] args) {
		String fileName = DEFAULT_FILE_NAME;
		int duration = DEFAULT_DURATION;
		int qps = DEFAULT_QPS;

		// 简单命令行解析
		for (int i = 0; i < args.length; i++) {
			if ("--file".equals(args[i]) && i + 1 < args.length) {
				fileName = args[++i];
			}
			else if ("--duration".equals(args[i]) && i + 1 < args.length) {
				duration = Integer.parseInt(args[++i]);
			}
			else if ("--qps".equals(args[i]) && i + 1 < args.length) {
				qps = Integer.parseInt(args[++i]);
			}
		}

		System.out.println("INFO: 未指定 --file，使用默认测试文件: " + fileName);

		FileWithPath fileWithPath = findFileByName(fileName);
		if (fileWithPath == null) {
			System.err.println("ERROR: 文件 '" + fileName + "' 未找到或不支持");
			System.exit(1);
		}

		try {
			Files.createDirectories(OUTPUT_DIR);
			runDurationTest(fileWithPath, duration, qps).join();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 根据文件名查找文件路径和所属子目录
	 */
	private static FileWithPath findFileByName(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex == -1) {
			System.err.println("错误：文件必须包含扩展名，例如 '10m.mp4'，你输入的是: " + filename);
			return null;
		}

		String ext = filename.substring(dotIndex + 1).toLowerCase();
		String subdir = TYPE_MAP.get(ext);
		if (subdir == null) {
			System.err.println("错误：不支持的文件类型 ." + ext);
			return null;
		}

		Path filePath = INPUT_DIR.resolve(subdir).resolve(filename);
		if (!Files.exists(filePath)) {
			System.err.println("错误：文件未找到 → " + filePath);
			System.err.println("提示：请确认文件存在于 " + INPUT_DIR.resolve(subdir) + " 目录下");
			return null;
		}

		return new FileWithPath(filePath, subdir);
	}

	/**
	 * 执行持续压测
	 */
	private static CompletableFuture<Void> runDurationTest(FileWithPath fileWithPath, int duration, int qps) throws IOException {
		Path filePath = fileWithPath.path;
		String subdir = fileWithPath.subdir;
		String fileName = filePath.getFileName().toString();
		String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		long fileSizeBytes = filePath.toFile().length();
		double fileSizeMb = fileSizeBytes / (1024.0 * 1024.0);
		String fileType = Files.probeContentType(filePath);
		if (fileType == null)
			fileType = "application/octet-stream";

		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

		System.out.println("INFO: 开始持续压测 | 文件: " + fileName + " | 大小: " + String.format("%.2f", fileSizeMb) + " MB");
		System.out.println("INFO: 压测时长: " + duration + " 秒 | 目标 QPS: " + qps);
		System.out.println("--------------------------------------------------------------");

		long startTime = System.currentTimeMillis();
		long endTime = startTime + duration * 1000;

		RateLimiter rateLimiter = RateLimiter.create(qps); // Guava 限流器

		// 创建线程池，大小根据 QPS 调整
		ExecutorService executor = Executors.newFixedThreadPool(Math.min(300, qps * 5));

		int[] requestId = {0}; // 用于生成唯一ID

		List<CompletableFuture<Void>> tasks = new ArrayList<>();

		// 持续创建任务直到时间结束
		while (System.currentTimeMillis() < endTime) {
			rateLimiter.acquire(); // 获取令牌，阻塞直到可获取
			int currentId = ++requestId[0];

			String finalFileType = fileType;
			CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
				uploadFileOnce(currentId, filePath, fileName, finalFileType, fileSizeMb, "." + fileExt);
			}, executor);

			tasks.add(task);
		}

		System.out.println("INFO: 压测时间结束，等待剩余 " + tasks.size() + " 个请求完成...");

		CompletableFuture<Void> allDone = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
		allDone.join();

		executor.shutdown();
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		}
		catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

		long totalElapsed = System.currentTimeMillis() - startTime;
		System.out.println("--------------------------------------------------------------");
		double totalDuration = totalElapsed / 1000.0;
		System.out.println("INFO: 压测完成，实际运行时间: " + String.format("%.2f", totalDuration) + " 秒");

		saveDetailedLog(fileName, timestamp, subdir, duration, qps);
		saveSummary(fileName, timestamp, fileSizeMb, fileExt, subdir, duration, totalDuration, qps);

		return allDone;
	}

	/**
	 * 单次文件上传任务
	 */
	private static void uploadFileOnce(int uploadId, Path filePath, String fileName, String fileType, double fileSizeMb, String extension) {
		UploadResult result = new UploadResult();
		result.id = uploadId;
		result.file = fileName;
		result.sizeMb = fileSizeMb;
		result.startTime = LocalDateTime.now().toString();

		long start = System.currentTimeMillis();

		try {
			// 使用 OkHttp 构建 Multipart 请求
			RequestBody fileBody = RequestBody.create(filePath.toFile(), MediaType.parse(fileType));
			MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart("file", fileName, fileBody)
					.addFormDataPart("directoryName", ""); // 空值

			RequestBody requestBody = multipartBuilder.build();

			// 构建请求
			Request.Builder requestBuilder = new Request.Builder()
					.url(UPLOAD_URL)
					.post(requestBody);

			// 添加头信息
			for (Map.Entry<String, String> header : HEADERS.entrySet()) {
				if ("extension".equals(header.getKey())) {
					requestBuilder.addHeader(header.getKey(), extension);
				}
				else {
					requestBuilder.addHeader(header.getKey(), header.getValue());
				}
			}

			// 执行请求
			try (Response response = HTTP_CLIENT.newCall(requestBuilder.build()).execute()) {
				long end = System.currentTimeMillis();
				double elapsed = (end - start) / 1000.0;
				result.elapsed = elapsed;
				result.statusCode = response.code();
				String responseBody = response.body() != null ? response.body().string() : "";

				try {
					JsonNode json = JSON_MAPPER.readTree(responseBody);
					result.response = json.toString();
					boolean success = json.has("code") && json.get("code").asInt() == 10000;
					result.success = success;

					if (success) {
						JsonNode pathNode = json.path("data").path("Path");
						result.fileUrl = pathNode.asText("N/A");
					}
				}
				catch (Exception e) {
					result.response = "{raw: " + (response.body() != null ? responseBody.substring(0, Math.min(100, responseBody.length())) : "empty") + "...}";
				}

				String status = result.success ? "SUCCESS" : "FAILED";
				System.out.printf("[%s] [%d] 上传完成 | 耗时: %.3fs | 状态码: %d%n", status, uploadId, elapsed, response.code());
				if (result.success) {
					System.out.println("Link: " + result.fileUrl);
				}
			}

		}
		catch (Exception e) {
			long end = System.currentTimeMillis();
			double elapsed = (end - start) / 1000.0;
			result.elapsed = elapsed;
			result.response = "{error: " + e.getMessage() + "}";
			System.out.printf("[ERROR] [%d] 请求异常 | 耗时: %.3fs | 错误: %s%n", uploadId, elapsed, e.getMessage());
		}

		synchronized (LOCK) {
			ALL_RESULTS.add(result);
		}
	}

	/**
	 * 保存详细日志
	 */
	private static void saveDetailedLog(String fileName, String timestamp, String subdir, int duration, int qps) {
		Path outputSubdir = OUTPUT_DIR.resolve(subdir);
		try {
			Files.createDirectories(outputSubdir);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Path detailPath = outputSubdir.resolve("detail_" + fileName + "_" + timestamp + ".txt");

		try (PrintWriter f = new PrintWriter(Files.newBufferedWriter(detailPath, StandardCharsets.UTF_8))) {
			f.println("========== 文件上传详细日志 ==========");
			f.println("生成时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			f.println("测试文件: " + fileName);
			f.println("压测时长: " + duration + " 秒");
			f.println("目标 QPS: " + qps);
			f.println("实际请求数: " + ALL_RESULTS.size());
			f.println("--------------------------------------------------------------");
			f.printf("%-4s %-8s %-6s %-6s %s%n", "ID", "耗时(秒)", "状态码", "成功", "返回链接");
			f.println("---- -------- ------ ------ ----------------------------------");

			List<UploadResult> sorted = ALL_RESULTS.stream()
					.sorted(Comparator.comparingInt(r -> r.id))
					.collect(Collectors.toList());

			for (UploadResult res : sorted) {
				String successStr = res.success ? "YES" : "NO";
				String elapsedStr = res.elapsed != null ? String.format("%.3f", res.elapsed) : "-";
				String statusCodeStr = res.statusCode != null ? res.statusCode.toString() : "-";
				String path = res.fileUrl != null ? res.fileUrl : "N/A";
				if (res.response != null && res.response.contains("error")) {
					path = "[ERROR] " + res.response;
				}
				else if (res.response != null && res.response.length() > 50) {
					path = res.response.substring(0, 50) + "...";
				}

				f.printf("%-4d %-8s %-6s %-6s %s%n", res.id, elapsedStr, statusCodeStr, successStr, path);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("INFO: 详细日志已保存至 " + detailPath);
	}

	/**
	 * 保存统计摘要
	 */
	private static void saveSummary(String fileName, String timestamp, double fileSizeMb, String fileExt,
									String subdir, int duration, double totalDuration, int qps) {
		if (ALL_RESULTS.isEmpty())
			return;

		List<Double> times = ALL_RESULTS.stream()
				.map(r -> r.elapsed)
				.filter(Objects::nonNull)
				.sorted()
				.collect(Collectors.toList());

		int total = times.size();
		long success = ALL_RESULTS.stream().filter(r -> r.success).count();
		double successRate = 100.0 * success / total;
		double avgTime = times.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
		double minTime = times.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		double maxTime = times.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

		double p99 = total >= 10 ? percentile(times, 99) : -1;
		double p999 = total >= 10 ? percentile(times, 99.9) : -1;

		double actualQps = total / (double) totalDuration;

		Path outputSubdir = OUTPUT_DIR.resolve(subdir);
		Path summaryPath = outputSubdir.resolve("summary_" + fileName + "_" + timestamp + ".txt");

		try (PrintWriter f = new PrintWriter(Files.newBufferedWriter(summaryPath, StandardCharsets.UTF_8))) {
			f.println("========== 压测统计摘要 ==========");
			f.println("生成时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			f.println("测试文件: " + fileName);
			f.println("文件类型: " + fileExt.toUpperCase());
			f.println("文件大小: " + String.format("%.2f", fileSizeMb) + " MB");
			f.println("压测时长: " + duration + " 秒");
			f.println("实际总耗时: " + totalDuration + " 秒");
			f.println("目标 QPS: " + qps);
			f.println("实际请求数: " + total);
			f.println("实际平均 QPS: " + String.format("%.2f", actualQps));
			f.println("成功数: " + success);
			f.println("失败数: " + (total - success));
			f.println("成功率: " + String.format("%.2f", successRate) + "%");
			f.println("平均响应时间: " + String.format("%.3f", avgTime) + " 秒");
			f.println("最快响应: " + String.format("%.3f", minTime) + " 秒");
			f.println("最慢响应: " + String.format("%.3f", maxTime) + " 秒");
			f.println("P99 响应时间: " + (p99 == -1 ? "N/A" : String.format("%.3f", p99)) + " 秒");
			f.println("P99.9 响应时间: " + (p999 == -1 ? "N/A" : String.format("%.3f", p999)) + " 秒");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("INFO: 统计摘要已保存至 " + summaryPath);
	}

	/**
	 * 计算百分位数
	 */
	private static double percentile(List<Double> data, double percentile) {
		if (data.isEmpty())
			return 0.0;
		int index = (int) Math.ceil(percentile / 100.0 * data.size()) - 1;
		index = Math.max(0, Math.min(index, data.size() - 1));
		return data.get(index);
	}

	// ===== 辅助类 =====

	/**
	 * 封装文件路径和其所属子目录
	 */
	static class FileWithPath {
		Path path;
		String subdir;

		FileWithPath(Path path, String subdir) {
			this.path = path;
			this.subdir = subdir;
		}
	}

	/**
	 * 单次上传结果
	 */
	static class UploadResult {
		int id;
		String file;
		double sizeMb;
		String startTime;
		Double elapsed;     // 耗时(秒)
		Integer statusCode; // HTTP 状态码
		boolean success;    // 是否成功
		String response;    // 响应内容
		String fileUrl;     // 成功时的文件链接
	}
}