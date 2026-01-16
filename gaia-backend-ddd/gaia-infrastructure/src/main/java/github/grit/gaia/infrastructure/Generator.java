package github.grit.gaia.infrastructure;


import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class Generator {

    // ========================= 数据库连接信息 =========================
    private static final String URL = "jdbc:mysql://localhost:3306/gaia?useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // ========================= 代码生成表 =========================
    private static final String[] TABLES = {"gaia_tenant", "gaia_tenant_subscription", "gaia_account",
            "gaia_tenant_account", "gaia_account_integrate"
    };

    // ========================= 输出路径 =========================
    private static final String INFRA_JAVA_ROOT = "gaia-infrastructure/src/main/java";
    private static final String ENTITY_PATH = INFRA_JAVA_ROOT + "/github/grit/gaia/infrastructure/persistence/entity";
    private static final String MAPPER_XML_PATH = "gaia-infrastructure/src/main/resources/mapper";

    public static void main(String[] args) {

        // 生成路径映射
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.entity, ENTITY_PATH);
        pathInfo.put(OutputFile.xml, MAPPER_XML_PATH);

        // 执行代码生成器
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> builder
                        .author("plato")
                        .commentDate("yyyy-MM-dd HH:mm:ss")
                        .disableOpenDir()
                        .outputDir(INFRA_JAVA_ROOT)
//                        .enableSwagger()
                )
                // 包结构配置
                .packageConfig(builder -> builder
                        .parent("github.grit.gaia.infrastructure.persistence")
                        .entity("entity")
                        .service("repository")
                        .serviceImpl("repository.impl")
                        .pathInfo(pathInfo)
                )
                // 策略配置
                .strategyConfig(builder ->
                        builder
                                .addInclude(TABLES)
                                .addTablePrefix("sys_")

                                .entityBuilder()
                                .enableLombok()
                                .naming(NamingStrategy.underline_to_camel)
                                .columnNaming(NamingStrategy.underline_to_camel)

                                .mapperBuilder()

                                .serviceBuilder()
                                .formatServiceFileName("I%sRepository")
                                .formatServiceImplFileName("%sRepositoryImpl")
                )
                .templateConfig(builder -> builder
                        .disable(TemplateType.CONTROLLER)
                        .service("/templates/repository.java.vm")      // ⭐ 自定义模板路径
                        .serviceImpl("/templates/repositoryImpl.java.vm") // ⭐ 可选：自定义实现类
                )
                .execute();
    }
}

