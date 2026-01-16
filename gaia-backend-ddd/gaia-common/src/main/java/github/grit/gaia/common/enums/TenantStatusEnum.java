package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TenantStatusEnum {
	ENABLE("ENABLE", "启用"),
	DISABLE("DISABLE", "禁用"),
	;
	private final String code;
	private final String name;
}
