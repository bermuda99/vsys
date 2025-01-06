package actor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/*
User-Thread:
Ein User-Thread ist ein normaler Thread, der nicht als Daemon-Thread markiert ist.
Diese Threads verhindern das Beenden des Programms, solange sie noch aktiv sind.
 */

public class UserThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable runnableTask) {
        Thread thread = Executors.defaultThreadFactory().newThread(runnableTask);
        thread.setDaemon(false);
        return thread;
    }
}
