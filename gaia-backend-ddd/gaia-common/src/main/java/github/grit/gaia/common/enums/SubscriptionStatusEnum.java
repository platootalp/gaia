package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionStatusEnum {
	ACTIVE("ACTIVE", "生效"),
	EXPIRE("EXPIRE", "过期"),
	CANCEL("CANCEL", "取消"),
	;
	private final String code;
	private final String name;
}
