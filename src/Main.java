public class Main {
    public static void main(String[] args) {
        var coroutine = new Coroutine();
        coroutine.start(yield -> {
            for (int i = 0; ; i++) {
                var time = System.currentTimeMillis();
                yield.accept(() -> System.currentTimeMillis() - time < 1000);
                System.out.println(i);
            }
        });

        coroutine.start(yield -> {
            for (;;) {
                var time = System.currentTimeMillis();
                yield.accept(() -> System.currentTimeMillis() - time < 2000);
                System.out.println("-----");
            }
        });

        for (; ; ) {
            coroutine.update();
        }
    }
}