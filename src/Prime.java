import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Prime {
	private static int cpu = 4;
	private static long number = 1000;
	private static boolean print_f = false;
	
	public static void main(final String[] args) {
		
		// タイマー開始
		long t1 = System.nanoTime();
		
		// 引数の受け取り
		for (int i=0; i<args.length; i++) {
			if ("-cpu".equals(args[i])) {
				cpu = Integer.parseInt(args[++i]);
			} else if ("-num".equals(args[i])) {
				number = Long.parseLong(args[++i]);
			} else if ("-print".equals(args[i])) {
				print_f = true;
			}
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(cpu);
		
		final long div_size = 100;
		final long task_num = (number + div_size - 1) / div_size;
		
		List<Future<List<Long>>> futures = new ArrayList<Future<List<Long>>>();
		for (long i=0; i < task_num; i++) {
			final long start = div_size * i;
			final long end   = div_size * (i+1);
			futures.add(executor.submit(new Runner(start, end)));
		}
		
		executor.shutdown();
		
		List<Long> totalPrimes = new ArrayList<Long>();
        for (Future<List<Long>> future : futures) {
			try {
				List<Long> primes = future.get();
				totalPrimes.addAll(primes);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (print_f) {
        	Collections.sort(totalPrimes);
        	for (Long prime : totalPrimes) {
        		System.out.print(prime + ", ");
        	}
        }
        
		long t2 = System.nanoTime();
		System.out.print("\n\nExecution time: " + ((t2-t1)/1000) + " micro seccond.\n");
	}
}
