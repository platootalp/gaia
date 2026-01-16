package github.grit.gaia.domain.model;

import github.grit.gaia.common.enums.TenantStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static github.grit.gaia.common.enums.TenantStatusEnum.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tenant {

    private Long id;
    private String name;
    private String encryptPublicKey;
    private String status;
    private String customConfig;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 工厂方法：创建租户时必须执行的一些逻辑
    public static Tenant create(String name, String encryptPublicKey, String customConfig) {
        return Tenant.builder()
                .name(name)
                .encryptPublicKey(encryptPublicKey)
                .status(ENABLE.getCode())
                .customConfig(customConfig)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private static void validateName(String name) {
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Invalid tenant name");
        }
    }

    public void disable() {
        if (DISABLE.getCode().equals(this.status)) {
            throw new IllegalStateException("Tenant is already disabled");
        }
        this.status = DISABLE.getCode();
        this.updatedAt = LocalDateTime.now();
    }

    public void enable() {
        if (ENABLE.getCode().equals(this.status)) {
            throw new IllegalStateException("Tenant is already enabled");
        }
        this.status = ENABLE.getCode();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateConfig(String config) {
        this.customConfig = config;
        this.updatedAt = LocalDateTime.now();
    }

}

