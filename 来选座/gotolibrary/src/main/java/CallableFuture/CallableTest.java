package CallableFuture;

import java.util.concurrent.Callable;

public class CallableTest implements Callable {
    private String threadName ;

    public CallableTest(String threadName) {
        this.threadName = threadName;
    }
    @Override
    public Object call() throws Exception {
        return threadName+"返回的信息";
    }

}
