# EzCoroutine

This project demonstrates a coroutine-like implementation in Java using `ArrayList` and synchronization primitives (`wait()` and `notify()`). It allows the creation and management of coroutines where you can yield execution based on a condition, and resume execution after the condition is met.

## Features

- **Coroutine-like behavior**: Yield execution and resume based on custom conditions.
- **Simple API**: Easy to define coroutines using lambda expressions.
- **Thread synchronization**: Manages threads using `synchronized`, `wait()`, and `notify()` to pause and resume execution.

## How It Works

The `Coroutine` class extends `ArrayList<Coroutine.Start>` to manage coroutines. Each coroutine is represented by a `Start` interface, which uses `Consumer<Supplier<Boolean>>` to yield execution until the given condition (in the form of a `Supplier<Boolean>`) is satisfied.

- `start()`: Adds a new coroutine and starts its execution in a separate thread.
- `update()`: Resumes the next coroutine in the list by calling `next()`.

### Coroutine Example

```java
public class Main {
    public static void main(String[] args) {
        var coroutine = new Coroutine();

        // Coroutine that prints an incrementing number every second
        coroutine.start(yield -> {
            for (int i = 0; ; i++) {
                var time = System.currentTimeMillis();
                yield.accept(() -> System.currentTimeMillis() - time < 1000);
                System.out.println(i);
            }
        });

        // Coroutine that prints a separator every 2 seconds
        coroutine.start(yield -> {
            for (;;) {
                var time = System.currentTimeMillis();
                yield.accept(() -> System.currentTimeMillis() - time < 2000);
                System.out.println("-----");
            }
        });

        // Continuously update coroutines
        for (; ; ) {
            coroutine.update();
        }
    }
}
