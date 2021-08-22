package test;

/**
 * 测试JVM给对象分配内存
 *
 * VM参数
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * GC输出日志(JDK版本 1.8.0_60)
 *
 * [GC (Allocation Failure) [PSYoungGen: 6264K->808K(9216K)] 6264K->4912K(19456K), 0.0032876 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Heap
 *  PSYoungGen      total 9216K, used 7160K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 8192K, 77% used [0x00000000ff600000,0x00000000ffc34020,0x00000000ffe00000)
 *   from space 1024K, 78% used [0x00000000ffe00000,0x00000000ffeca020,0x00000000fff00000)
 *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *  ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
 *  Metaspace       used 3263K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
 *
 *   很明显，需要4MB连续内存空间的大对象allocation4直接进入了老年代ParOldGen。allocation1 2 3 这三个对象进入了年轻代PSYoungGen
 *
 *   使用  java -XX:+PrintCommandLineFlags -version  命令打印出机器上JVM的各项配置参数，
 *   可以看到 -XX:+UseParallelGC
 *
 *   对于JRE1.7和JRE1.8，它们使用的是 Parallel Scavenge和 Parallel Old这两种GC的组合。（在Server VM模式（默认）下）
 *
 */
public class TestAllocation {
    public static void main(String[] args) {
        testAllocation();
    }

    private static final int _1MB = 1024 * 1024;

    private static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4,allocation5;
        allocation1 = new byte[_1MB * 2];
        allocation2 = new byte[_1MB * 2];
        allocation3 = new byte[_1MB * 2];

        allocation4 = new byte[_1MB * 4];

        // PSYoungGen      total 9216K, used 5000K
        // ParOldGen       total 10240K, used 6145K
        // 年轻代剩余4216k空间，不够放置6MB对象；老年代剩余4095k空间，仍然不够放置6MB对象；所以最终 outOfMemoryError OOM
        allocation5 = new byte[_1MB * 6];
    }

}
