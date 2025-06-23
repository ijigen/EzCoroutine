import java.util.function.Supplier;

public interface Coroutine {
    default void start(Runnable runnable) {
        synchronized (this) {
            Thread.startVirtualThread(() -> {
                runnable.run();
                synchronized (this) {
                    notify();
                }
            });
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    default void ezYield(Supplier<Boolean> yield) {
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
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}