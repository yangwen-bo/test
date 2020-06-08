package forkJoin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author yangwen-bo
 * @Date 2020/6/8.
 * @Version 1.0
 *
 * 初步简单使用forkjoin
 */
public class ForkJoinDemo {

    /**
     * ForkJoinPool是最外层运行任务的“池子”
     *
     * ForkJoinPool由ForkJoinTask数组和ForkJoinWorkerThread数组组成，
     * ForkJoinTask数组负责存放程序提交给ForkJoinPool的任务，
     * 而ForkJoinWorkerThread数组负责执行这些任务
     *
     * ForkJoinTask就是ForkJoinPool里面的每一个任务。
     * 他主要有两个子类：
     * RecursiveAction（一个递归无结果的ForkJoinTask（没有返回值））
     * 和RecursiveTask（一个递归有结果的ForkJoinTask（有返回值））。
     * 然后通过fork()方法去分配任务执行任务，通过join()方法汇总任务结果
     *
     * ForkJoinTask在执行的时候可能会抛出异常，在主线程中是无法直接获取的，
     * 但是可以通过ForkJoinTask提供的isCompletedAbnormally()方法来检查任务是否已经抛出异常或已经被取消了
     *
     */

    /**
     * RecursiveAction是没有返回值的 compute处理后没有返回值
     * ForkJoinPool submit之后没有返回值，
     * 可以通过forkJoinPool.awaitQuiescence( 100, TimeUnit.MILLISECONDS )阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
     *
     *
     */

    // RecursiveTask一个递归有结果的ForkJoinTask（有返回值）
    private static class MyRecursiveTask extends RecursiveTask<Integer>{
        //规定的任务拆分最小值
        private final int THRESHOLD = 10;

        private final int start;
        private final int end;
        public MyRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            //使用二分法细分任务，如果任务小的不能再细分，则直接计算
            if (end-start<=THRESHOLD){
                // 任务已不能拆分，直接计算
                // 这里是求给定两个数之间的数求和，rangeClosed包含结束节点
                return IntStream.rangeClosed( start,end ).sum();
            }else{
                // 使用二分法 细分
                int mid = (start + end)/2;
                // 细分任务
                MyRecursiveTask leftTask = new MyRecursiveTask( start, mid );
                MyRecursiveTask rightTask = new MyRecursiveTask( mid + 1, end );
                // fork()方法去分配任务执行任务
                leftTask.fork();
                rightTask.fork();
                // join()方法汇总任务结果
                return leftTask.join()+rightTask.join();
            }
        }
    }

    public static void main(String[] args) {
        // 池
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 通过池的submit方法，执行任务
        ForkJoinTask result = forkJoinPool.submit( new MyRecursiveTask( 0,100 ) );

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
