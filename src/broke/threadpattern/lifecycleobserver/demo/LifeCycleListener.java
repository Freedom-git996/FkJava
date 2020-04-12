package broke.threadpattern.lifecycleobserver.demo;

public interface LifeCycleListener {

    void onEvent(ObservableRunnable.RunnableEvent event);
}
