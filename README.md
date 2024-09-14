# Coroutine Implementation in Java

This project demonstrates a simplified coroutine-like implementation in Java using an interface-based approach. It provides an easy-to-use coroutine system where you can start and manage coroutines, allowing them to yield execution based on conditions.

## Features

- **Simplified Coroutine API**: Easy to start and manage coroutines using `Runnable` and `Supplier<Boolean>`.
- **No need for additional structures**: The `Coroutine` is implemented as an interface, simplifying usage and structure.
- **Mutual exclusion**: Ensures that only one coroutine runs at any given time.

## How It Works

The `Coroutine` interface provides three main methods to manage coroutines:

- `start(Runnable runnable)`: Starts a coroutine, which runs its logic in a separate thread.
- `yield(Supplier<Boolean> condition)`: Allows a coroutine to yield execution until a condition is met.
- `update()`: Wakes up the coroutine that is currently in a waiting state, allowing it to continue.

### Coroutine Example

```java
public class Main implements Coroutine {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        start(this::coroutine1);  // Start first coroutine
        start(this::coroutine2);  // Start second coroutine
        for (;;) {
            update();  // Continuously update coroutines
        }
    }

    public void coroutine1() {
        for (int i = 0; ; i++) {
            var time = System.currentTimeMillis();
            yield(() -> System.currentTimeMillis() - time < 1000);  // Yield for 1 second
            System.out.println(i);  // Print incrementing number
        }
    }

    public void coroutine2() {
        for (;;) {
            var time = System.currentTimeMillis();
            yield(() -> System.currentTimeMillis() - time < 2000);  // Yield for 2 seconds
            System.out.println("-----");  // Print separator
        }
    }
}
