package github.grit.gaia.demo;

import java.io.File;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class FileUploadExample {

	public static void main(String[] args) {
		// 1. 准备文件和参数
		String filePath = "/Users/lijunyi/road/gaia/gaia-demo/src/test/resources/test.jpg";  // 修改为你的文件路径
		String url = "https://shop-gateway.tuhutest.cn/file-upload/v2/file/upload/stream/public/image";

		String extension = ".jpeg";
		String directoryName = "";  // 可选目录名，留空也行

		// 2. 创建 RestTemplate（无需任何配置）
		RestTemplate restTemplate = new RestTemplate();

		// 3. 构建 multipart 表单数据
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		formData.add("file", new FileSystemResource(new File(filePath)));

		// 4. 构建带查询参数的 URL
		String fullUrl = UriComponentsBuilder
				.fromHttpUrl(url)
				.queryParam("extension", extension)
				.queryParam("directoryName", directoryName)
				.toUriString();

		// 5. 设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Accept", "*/*");
		headers.set("Connection", "keep-alive");

		// 如果是鉴权接口，取消下面两行注释并填上 token
		// headers.set("auth-type", "boss");
		// headers.set("Authorization", "Bearer your-jwt-token-here");

		// 6. 构建请求
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, headers);

		// 7. 发送请求
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, request, String.class);

			// 8. 输出结果
			System.out.println("响应状态码: " + response.getStatusCodeValue());
			System.out.println("响应内容: " + response.getBody());

		}
		catch (Exception e) {
			System.err.println("上传失败: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
