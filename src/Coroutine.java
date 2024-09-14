import java.util.function.Consumer;
import java.util.function.Supplier;

public class Coroutine {
    public void start(Consumer<Consumer<Supplier<Boolean>>> s) {
        synchronized (this) {
            new Thread(() -> {
                s.accept(yield -> {
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
                });
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

    public void update() {
        synchronized (this) {
            notify();
        }
    }
}
