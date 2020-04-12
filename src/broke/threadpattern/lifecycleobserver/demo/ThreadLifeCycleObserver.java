package broke.threadpattern.lifecycleobserver.demo;

import java.util.Arrays;
import java.util.List;

public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            return;
        }
        ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id: " + id);
                    Thread.sleep(1000L);
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("the runnable [" + event.getThread().getName() + "], state is [" + event.getState() + "]");
            if(event.getCause() != null) {
                System.out.println(", cause is [" + event.getCause().getMessage() + "]");
            }
        }
    }

    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
        Thread thread = new Thread();
        thread.getState();
    }
}
