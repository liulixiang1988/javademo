import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {

    private static FutureTask<String> service() {
        FutureTask<String> futureTask = new FutureTask<>(() -> "Do something");
        new Thread(futureTask).start();
        return futureTask;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = service();
        System.out.println("hello");
        System.out.println(task.get());
    }
}
