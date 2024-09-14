import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Coroutine {
    default void start(Runnable runnable) {
        synchronized (this) {
            new Thread(() -> {
                runnable.run();
                synchronized (this) {
                    notify();
                }
            }).start();
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    default void yield(Supplier<Boolean> yield) {
        do {
            synchronized (this) {
                notify();
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } while (yield.get());
    }

    default void update() {
        synchronized (this) {
            notify();
        }
    }
}
