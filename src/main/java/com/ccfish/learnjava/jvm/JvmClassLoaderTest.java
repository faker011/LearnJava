package com.ccfish.learnjava.jvm;

/**
 * 双亲加载机制
 *     系统类，由系统类加载器负责 Bootstrap
 *     自定义类由其他的加载器负责 AppClassLoader
 * Java运行时数据区
 *      方法区：最重要的内存区域，多线程共享，保存了类的信息（名称，成员，接口，父类），反射机制是重要的组成部分，动态
 *              进行类操作的实现
 *      堆内存（Heap）：保存对象的真实信息，该内存牵扯到释放问题（GC）；
 *      栈内存（Stack）：线程的私有空间，每一次进行方法调用时都会存在栈帧，采用先进后出的设计原则
 *              -》本地变量表：局部参数/形参，允许保存有32位的插槽（solt），如果超过了32位的长度就需要开辟两个连续
 *                              性的插槽（long，double）---volatile关键字问题
 *              -》操作数栈： 执行所有的方法计算操作
 *              -》常量池引用：String类， Integer类实例
 *              -》返回地址：方法执行完毕后恢复执行的点
 *      程序计数器：执行指令的一个顺序编码，该区域的所占比例几乎可以忽略
 *      本地方法栈：与栈内存类似，区别是为本地方法服务的
 *
 * JVM分类
 *      Java是通过指针进行程序访问，所以它没有采用句柄的形式操作，这样使得程序性能更高。
 *      传统意义来讲，JVM分为三种（虚拟机是一个公共标准）
 *          【sun】从JDK 1.2开始使用hotsopt虚拟机标准
 *          【BEA】使用JRockit标准，例如像WebLogic
 *          【IBM】开发JVM‘S（J9）虚拟机
 * 虚拟机提供三种处理模式：
 *      混合模式：java -version
 *      纯解释模式： 【禁用JIT】java -Xint -version （JIT：即时编译）
 *      纯编译模式： java -Xcomp -version
 * 运行模式
 *      【client】客户端运行：启动速度快，中间程序执行慢，占用内存小
 *      【server】服务器运行：启动速度慢，中间程序执行效率高，占用内存多
 *
 * JVM内存模型
 *      合理的内存模型可以使GC的性能更加强大，不必太大的浪费服务器的性能，从而减少阻塞所带来的程序的性能的影响
 *      Java中数据保存的内存位置：堆内存（java调优，原理都是在这个位置）
 *           最需要强调的就是1.8之后带来的内存结构改变和GC策略提升
 *      内存模型参见doc文件夹下
 *      伸缩区空间大小：
 *          电脑内存16G,所以会发现默认的内容：（参见RuntimeTest.java）
 *              MaxMemory：整体电脑内存的1/4
 *              TotalMemory:整体内存的1/64
 *                  伸缩区的空间：MaxMemory-TotalMemory
 *              程序执行时设置有相关的参数：
 *                  -Xmx 分配的最大初始化内存
 *                  -Xms 最大的分配内存
 *                  eg: java -Xms大小单位 -Xms大小单位
 *
 * GC处理流程
 *      1.对象的实例化需要关键字new完成，所有的新对象都会在伊甸园区开辟，
 *        如果伊甸园区内存不足，会发生MinorGC
 *      2.伊甸园区不是无限大的，所以肯定有些对象执行了N此的MinorGC后还会存在；
 *        那么这些对象将进入到存活区（存活区有两个，一个负责保存存活对象，一个负责晋升，永远都有一个空内存）
 *      3.经历过若干次的MinorGC回收处理之后发现空间依然不够用，则进行老年代的GC回收，
 *        执行MajorGC（full GC，性能很差），如果可以回收空间，则继续进行MinorGC，
 *      4.如果MajorGC失败，则继续内存已经占用满，则抛出OOM异常 -XX:PrintGCDetails
 *      (JDK1.8时，默认会根据系统的不同来选择不同的GC策略，在1.9-1.11时使用的默认的GC操作就是G1处理）
 *
 * 内存回收算法
 *      年轻代的回收算法
 *          1.“复制”清理算法：将保留的对象复制到存活区之中，存活区的内容会保存到老年代之中；
 *          2.伊甸园区总是会有大量的新对象的产生，所以HotSpot虚拟机使用了BTP,TLAB技术形式进行处理
 *            a. BTP: 单一CPU时代所有的对象依次保存
 *            b. TLAB: 拆分为不同的块（根据CPU核心个数进行拆分）
 *      老年代的回收算法
 *          “标记”清除算法： 先进行对象的第一次标记，在这段时间内会暂停程序的执行（如果标记的时间
 *                              长或者对象的内容过多），这个暂停的时间就会长；
 *                  会产生串行标记，并行标记的使用问题：
 *          “标记”压缩算法： 基于标记清除算法，将零散的内存空间进行整理重新集合再分配
 *            注：STW（stop-the-world)设计问题，暂时挂起所有的程序线程，进行无用的对象标记
 * G1(1.8以后)
 *      优点：支持大内存4-64G， 支持多CPU 减少STW问题，可以保证并发状态下的程序执行
 *      具体：G1算法将内存空间分成很多个块，每一块都有对应的新生代，老年代等区，然后对其中的小块进行GC操作
 *              只影响其中小块对应的部分，对整体影响不大
 *      使用：（1.8）  -XX:UseG1GC
 *             (1.11)  默认是G1，不需要特殊配置
 * Tomcat调优
 *      修改 /bin/catalina.sh
 *          JAVA_OPTS="-Xms4096m -Xmx4096m -Xss1024k -XX:UseG1GC"
 *          1.-Xms4096m -Xmx4096m 取消伸缩区 ,以避免每次垃圾回收完成后JVM重新分配内存.
 *          2.-Xss1024k 设置每个线程的堆栈大小1024k
 *          3.-XX:UseG1GC 1.8使用G1算法
 *
 */
public class JvmClassLoaderTest {
    public static void main(String[] args) {
        String str = new String();
        System.out.println(str.getClass().getClassLoader()); //系统类null
        Member member = new Member();
        System.out.println(member.getClass().getClassLoader()); //自定义类AppClassLoader
        System.out.println(member.getClass().getClassLoader().getParent()); //ExtClassLoader
        System.out.println(member.getClass().getClassLoader().getParent().getParent()); //null->Bootstrap由JDK实现
    }
}
class Member{}
