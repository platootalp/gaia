package github.grit.gaia.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountRoleEnum {
	OWNER("owner", "所有者", "所有者"),
	ADMIN("admin", "管理员", "管理员"),
	EDITOR("editor", "编辑者", "编辑者"),
	NORMAL("normal", "普通用户", "普通用户"),
	;
	private final String code;
	private final String name;
	private final String desc;
}
