package CallableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFuture {
    public static void main(String[] args) {

        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(3) ;

        //创建三个有返回值的任务
        CallableTest callableTest1 = new CallableTest("我是线程1") ;
        CallableTest callableTest2 = new CallableTest("我是线程2") ;
        CallableTest callableTest3 = new CallableTest("我是线程3") ;

        Future future1 = pool.submit(callableTest1) ;
        Future future2 = pool.submit(callableTest2) ;
        Future future3 = pool.submit(callableTest3) ;

        try {
            System.out.println(future1.get().toString());
            System.out.println(future2.get().toString());
            System.out.println(future3.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally{
            pool.shutdown();
        }

    }

}
