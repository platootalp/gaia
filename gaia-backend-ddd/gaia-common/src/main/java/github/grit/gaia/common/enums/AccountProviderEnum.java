package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountProviderEnum {
	GITHUB("Github", "Github");
	private final String code;
	private final String name;
}
