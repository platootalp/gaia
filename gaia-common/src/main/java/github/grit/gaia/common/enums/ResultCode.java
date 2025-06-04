package github.grit.gaia.common.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lenovo
 * @version 1.0
 * @description 返回结果状态码及信息
 * @date 2024/3/9 16:35
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ResultCode {

	SUCCESS(200, "操作成功"),
	// 重定向
	REDIRECT(301, "redirect"),
	BADREQUEST(400, "请求错误"),
	UNAUTHORIZED(401, "未登录或token已经过期"),
	FORBIDDEN(403, "无权限访问"),
	NOT_FOUND(404, "资源不存在"),
	ERROR(500, "服务器错误"),
	;
	/**
	 * 状态码
	 */
	private final Integer code;
	/**
	 * 提示消息
	 */
	private final String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
