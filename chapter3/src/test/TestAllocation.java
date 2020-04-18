package test;

/**
 * 测试JVM给对象分配内存
 * VM参数
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * GC输出日志
 * Heap
 *  PSYoungGen      total 9216K, used 7822K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 8192K, 95% used [0x00000000ff600000,0x00000000ffda3bb0,0x00000000ffe00000)
 *   from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *   to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 *  ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
 *  PSPermGen       total 21504K, used 3190K [0x00000000f9a00000, 0x00000000faf00000, 0x00000000fec00000)
 *   object space 21504K, 14% used [0x00000000f9a00000,0x00000000f9d1db08,0x00000000faf00000)
 *
 *   很明显，需要4MB连续内存空间的大对象allocation4直接进入了老年代ParOldGen
 *   疑问：新生代PSYoungGen和永久代PSPermGen的内存分配过程是怎样的？为何会像日志打印的这样？
 *
 *   使用  java -XX:+PrintCommandLineFlags -version  命令打印出机器上JVM的各项配置参数，
 *   可以看到 -XX:+UseParallelGC  对于JRE1.7和JRE1.8都是这样，说明它们使用的是Parallel Scavenge和Parallel  Old这
 *   两种GC的组合。（在Server VM模式下）
 */
public class TestAllocation {
    public static void main(String[] args) {
        testAllocation();
    }

    private static final int _1MB = 1024 * 1024;

    private static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB * 2];
        allocation2 = new byte[_1MB * 2];
        allocation3 = new byte[_1MB * 2];

        allocation4 = new byte[_1MB * 4];
    }

}
