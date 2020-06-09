package forkJoin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.github.pagehelper.Page;

/**
 * @Author yangwen-bo
 * @Date 2020/6/9.
 * @Version 1.0
 *
 * 历史报表查询时使用forkjoin
 */
public class submitBillDemo {

    private static class MyRecursiveTask extends RecursiveTask< List<String> > {
        // 分小任务限制
        private static final int THRESHOLD = 2;

        private int start;
        private int end;

        public MyRecursiveTask( int start, int end) {
            this.start = start;
            this.end = end;
        }

        private List<String> addDate(){
            List<String> rows = null;
            for (int i = start; i < end; i++) {
                rows.add( "第"+i+"个数据" );
            }
            return rows;
        }

        @Override
        protected List<String> compute() {
            List<String> result = null;

            if ((end - start) <= THRESHOLD) {
                return addDate();
            }else {
                //使用二分法分配任务
                int inter = (start + end) / 2;
                MyRecursiveTask leftTask = new MyRecursiveTask(start,inter);
                leftTask.fork();
                MyRecursiveTask rightTask = new MyRecursiveTask(inter,end);
                rightTask.fork();

                // 将返回的list拼接
                result.addAll( leftTask.join() );
                result.addAll( rightTask.join() );

                return result;
            }
        }
    }


    public static void main(String[] args) {
        // 模拟查询页数操作，假设分页每页展示数据条数为5 ，各小任务（为查询2条数据）（这里用给list添加2条数据模拟查询到的2条数据）
        // 假设总共有13条数据
        // 初始化 分页对象

        /*Page<String> page = new Page<>();
        page.setCount( true );
        //页数 第几页
        page.setPageNum( 1 );
        //每页展示数据
        page.setPageSize( 5 );
        page.setStartRow( 0 );
        page.setEndRow( 0 );
        page.setTotal( 0 );
        page.setPages( 0 );
        page.setReasonable( true );
        page.setPageSizeZero( false );*/

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask< List<String> > result = pool.submit( new MyRecursiveTask(1,13));

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
