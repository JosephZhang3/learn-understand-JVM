package org.fenixsoft.clazz;

public class TestExceptionClass {
    private int m;

    public int inc() {
        return m + 1;
    }
}
/**
 * 任何方法都必有一个隐式参数this，即使方法签名中未显示，这点在class文件字节码中会有体现： args_size=1
 * u1即一个无符号int类型，即2位十六进制，即8位二进制，也就是一个字节
 */