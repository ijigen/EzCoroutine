import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Coroutine extends ArrayList<Coroutine.Start> {
    public interface Start extends Consumer<Consumer<Supplier<Boolean>>> {
        default void next() {
            synchronized (this) {
                notify();
            }
        }
    }
    public void start(Start start) {
        synchronized (this) {
            add(start);
            new Thread(() -> {
                start.accept(yield -> {
                    do {
                        synchronized (start) {
                            synchronized (this) {
                                notify();
                            }
                            try {
                                start.wait();
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
        forEach(Start::next);
    }
}
