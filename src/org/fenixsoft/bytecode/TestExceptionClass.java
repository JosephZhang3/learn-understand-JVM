package org.fenixsoft.bytecode;

public class TestExceptionClass {
    private int m = 3;

    private int inc() {
        int x;
        try {
            x = 10;
            return x;
        } catch (Exception e) {
            x = 11;
            return x;
        } finally {
            x = 12; // 这处赋值没有意义，任何情况都不可能返回12
        }
    }

    public static void main(String[] args) {
        System.out.println(new TestExceptionClass().inc());
    }

    /*

    inc 方法字节码分析：

  public int inc();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=5, args_size=1
         0: bipush        10
         2: istore_1
         3: iload_1                   // 保存x到 returnValue 中，此时 x=10
         4: istore_2
         5: bipush        12          // finally块中的 x = 12，注意 returnValue 依然是10，并不是12
         7: istore_1
         8: iload_2                   // 将 returnValue 中的值放到栈顶，准备给 ireturn 返回
         9: ireturn
        10: astore_2                  // 将 catch 中定义的 Exception e 赋值， 保存到 slot 2 中
        11: bipush        11
        13: istore_1
        14: iload_1                   // 保存x到 returnValue 中，此时 x=11
        15: istore_3
        16: bipush        12          // finally块中的 x = 12，注意 returnValue 是11，并不是12
        18: istore_1
        19: iload_3                   // 将 returnValue 中的值放到栈顶，准备给 ireturn 返回
        20: ireturn
        21: astore        4           // 如果出现了不属于 Exception 及其子类的异常，才会走到这里
        23: bipush        12          // finally块中的 x = 12，注意 returnValue 依然是10，并不是12
        25: istore_1
        26: aload         4           // 将异常放到栈顶
        28: athrow                    // 抛出异常，方法结束
      Exception table:
         from    to  target type
             0     5    10   Class java/lang/Exception
             0     5    21   any
            10    16    21   any
            21    23    21   any
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            3       7     1     x   I
           11      10     2     e   Ljava/lang/Exception;
           14       7     1     x   I
            0      29     0  this   Lorg/fenixsoft/bytecode/TestExceptionClass;
           26       3     1     x   I
    */

    /*
     * 补充：
     * 语义问题
     * finally 块中仅仅能修改类的静态成员变量，无法修改方法的局部变量，也无法修改类的非静态成员变量
     * 一般实践中仅做 close 释放资源的事情，不要试图改变状态！
     */
}