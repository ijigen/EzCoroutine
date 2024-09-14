public class Main implements Coroutine {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        start(this::coroutine1);
        start(this::coroutine2);
        for (; ; ) {
            update();
        }
    }

    public void coroutine1() {
        for (int i = 0; ; i++) {
            var time = System.currentTimeMillis();
            yield(() -> System.currentTimeMillis() - time < 1000);
            System.out.println(i);
        }
    }

    public void coroutine2() {
        for (; ; ) {
            var time = System.currentTimeMillis();
            yield(() -> System.currentTimeMillis() - time < 2000);
            System.out.println("-----");
        }
    }
}