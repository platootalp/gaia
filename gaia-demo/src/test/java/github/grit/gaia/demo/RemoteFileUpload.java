package github.grit.gaia.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RemoteFileUpload {

//	private static final String URL_PREFIX = "https://shop-gateway.tuhutest.cn/file-upload";
	private static final String URL_PREFIX = "http://localhost:9000";
	private static final RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		// 步骤一：获取上传授权信息
		String authUrl = URL_PREFIX + "/Utility/RemoteUpload/GetUploadAuthorization";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("directoryName", "lijunyi3");
		params.add("supportHeaders", "true");
		params.add("extensions", ".mp4");
		params.add("type", "2");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		headers.set("Connection", "keep-alive");
		// headers.set("auth-type", "boss");
		// headers.set("Authorization", "Bearer xxxxxx");

		HttpEntity<MultiValueMap<String, String>> authRequest = new HttpEntity<>(params, headers);
		System.out.println("正在获取上传授权信息...");

		try {
			ResponseEntity<String> authResponse = restTemplate.exchange(authUrl, HttpMethod.GET, authRequest, String.class);
			System.out.println("授权响应：" + authResponse.getStatusCode() + " - " + authResponse.getBody());

			if (authResponse.getStatusCode() != HttpStatus.OK) {
				System.out.println("授权请求失败，请检查API服务或参数");
				return;
			}

			// 步骤二：上传文件
			String authJson = authResponse.getBody();
			Map<String, Object> uploadInfo = parseJson(authJson); // 使用合适的JSON解析库来解析
			String uploadUri = (String) uploadInfo.get("Uri");
			Map<String, Object> formData = (Map<String, Object>) uploadInfo.get("Form");
			Map<String, String> uploadHeaders = (Map<String, String>) uploadInfo.get("Headers");
			String fileKey = (String) uploadInfo.getOrDefault("FileKey", "file");
			String filePath = "/Users/lijunyi/road/gaia/gaia-demo/src/test/resources/tBQ2oqiEtED8LYr0kSUhpw.mp4"; // 替换为本地文件路径

			System.out.println("准备上传文件：" + filePath + " 到 " + uploadUri);

			File file = new File(filePath);
			HttpHeaders uploadHeadersEntity = new HttpHeaders();
			uploadHeadersEntity.setAll(uploadHeaders);

			// 设置请求体（multipart/form-data）
			MultiValueMap<String, Object> uploadBody = new LinkedMultiValueMap<>();
			uploadBody.add(fileKey, new FileSystemResource(file));

			// 发送文件上传请求
			HttpEntity<MultiValueMap<String, Object>> uploadRequest = new HttpEntity<>(uploadBody, uploadHeadersEntity);
			System.out.println("上传文件中...");
			ResponseEntity<String> uploadResponse = restTemplate.exchange(uploadUri, HttpMethod.POST, uploadRequest, String.class);

			System.out.println("上传响应：" + uploadResponse.getStatusCode() + " - " + uploadResponse.getBody());
			if (uploadResponse.getStatusCode() != HttpStatus.NO_CONTENT) {
				System.out.println("上传失败，请检查响应内容");
				return;
			}

			// 步骤三：上报上传结果
			String etag = uploadResponse.getHeaders().getFirst("ETag");
			String location = uploadResponse.getHeaders().getFirst("Location");

			String reportUrl = URL_PREFIX + "/Utility/RemoteUpload/ReportUploadResult";
			Map<String, Object> reportBody = Map.of(
					"statusCode", uploadResponse.getStatusCodeValue(),
					"headers", List.of(
							Map.of("key", "ETag", "value", etag),
							Map.of("key", "Location", "value", location)
					)
			);

			HttpHeaders reportHeaders = new HttpHeaders();
			reportHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, Object>> reportRequest = new HttpEntity<>(reportBody, reportHeaders);

			ResponseEntity<String> reportResponse = restTemplate.exchange(reportUrl, HttpMethod.POST, reportRequest, String.class);
			System.out.println("上报响应：" + reportResponse.getStatusCode() + " - " + reportResponse.getBody());

			if (reportResponse.getStatusCode() == HttpStatus.OK) {
				String reportJson = reportResponse.getBody();
				Map<String, Object> reportData = parseJson(reportJson);
				String path = (String) reportData.get("Result");
				if (path != null) {
					System.out.println("视频上传成功，视频路径：https://v.tuhu.org/" + path);
				}
				else {
					System.out.println("上报结果缺少视频路径，请检查返回内容");
				}
			}
			else {
				System.out.println("上报失败，状态码: " + reportResponse.getStatusCode());
			}
		}
		catch (Exception e) {
			System.out.println("文件上传过程中出错: " + e.getMessage());
		}
	}

	// 用来解析JSON的方法
	private static Map<String, Object> parseJson(String json) {
		// 使用合适的JSON库（如Jackson）解析返回的JSON
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, Map.class);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
