package github.grit.gaia.agent.common.util;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON 工具类
 * 
 * @author Gaia Team
 */
@Slf4j
public class JsonUtil {
    
    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            log.error("对象转 JSON 失败", e);
            return null;
        }
    }
    
    /**
     * JSON 字符串转对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("JSON 转对象失败", e);
            return null;
        }
    }
}
