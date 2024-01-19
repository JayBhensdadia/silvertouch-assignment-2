import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException, java.util.concurrent.ExecutionException {
        
        CoroutineScheduler scheduler = new CoroutineScheduler(Executors.newFixedThreadPool(10));

        
        CompletableFuture<CompletableFuture<String>> result1 = scheduler.startCoroutine(() -> fetchData("https://google.com"));
        CompletableFuture<CompletableFuture<String>> result2 = scheduler.startCoroutine(() -> fetchData("https://google/friends.com"));

        

        
        CompletableFuture<String> data1 = result1.get();
        CompletableFuture<String> data2 = result2.get();

        System.out.println("Data 1: " + data1);
        System.out.println("Data 2: " + data2);

        
        scheduler.shutdown();
    }

    
    private static CompletableFuture<String> fetchData(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(url))
                        .timeout(Duration.ofSeconds(5))
                        .build();

                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                return response.body();
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch data from " + url, e);
            }
        });
    }

    
    private static class CoroutineScheduler {
        private final java.util.concurrent.Executor executor;

        CoroutineScheduler(java.util.concurrent.Executor executor) {
            this.executor = executor;
        }

        <T> CompletableFuture<T> startCoroutine(Coroutine<T> coroutine) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    T result = coroutine.run();
                    return result;
                } catch (Exception e) {
                    throw new RuntimeException("Coroutine execution failed", e);
                }
            }, executor);
        }

        void shutdown() {
            
            
        }
    }

    
    private interface Coroutine<T> {
        T run() throws Exception;
    }
}