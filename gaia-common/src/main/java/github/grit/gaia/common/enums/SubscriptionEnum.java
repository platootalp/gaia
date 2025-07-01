package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionEnum {
	FREE("free", "免费版", "免费版"),
	PLUS("plus", "增强版", "增强版"),
	PRO("pro", "专业版", "专业版"),
	ENTERPRISE("enterprise", "企业版", "企业版"),
	;
	private final String code;
	private final String name;
	private final String desc;
}
