package infra;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * 工具工具类
 * 提供各种实用的工具方法供AI Agent调用
 */
@Component
public class ToolUtil {

    /**
     * 计算两个数字的和
     */
    @Tool(description = "计算两个整数的和，返回计算结果")
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * 计算两个数字的差
     */
    @Tool(description = "计算两个整数的差，返回第一个数减去第二个数的结果")
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * 计算两个数字的乘积
     */
    @Tool(description = "计算两个整数的乘积，返回计算结果")
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * 计算两个数字的商
     */
    @Tool(description = "计算两个整数的商，返回第一个数除以第二个数的结果。注意：如果除数为0会抛出异常")
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }
        return (double) a / b;
    }

    /**
     * 计算数字的平方
     */
    @Tool(description = "计算一个整数的平方，返回该数的平方值")
    public int square(int number) {
        return number * number;
    }

    /**
     * 判断数字是否为偶数
     */
    @Tool(description = "判断一个整数是否为偶数，返回true表示是偶数，false表示是奇数")
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * 获取字符串的长度
     */
    @Tool(description = "获取字符串的长度，返回字符串中字符的个数")
    public int getStringLength(String text) {
        return text != null ? text.length() : 0;
    }

    /**
     * 将字符串转换为大写
     */
    @Tool(description = "将字符串转换为大写形式，返回转换后的大写字符串")
    public String toUpperCase(String text) {
        return text != null ? text.toUpperCase() : null;
    }

    /**
     * 将字符串转换为小写
     */
    @Tool(description = "将字符串转换为小写形式，返回转换后的小写字符串")
    public String toLowerCase(String text) {
        return text != null ? text.toLowerCase() : null;
    }

    /**
     * 反转字符串
     */
    @Tool(description = "反转字符串，返回反转后的字符串")
    public String reverseString(String text) {
        if (text == null) {
            return null;
        }
        return new StringBuilder(text).reverse().toString();
    }
}
