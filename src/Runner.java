import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

public class Runner implements Callable<List<Long>> {

	private final long start;
	private final long end;
	
	public Runner(final long start, final long end) {
		this.start = start;
		this.end   = end;
	}
	
	@Override
	public List<Long> call() {
		List<Long> primes = new ArrayList<Long>();
		for (long i=start; i<end; i++) {
			if (isPrime(i))
				primes.add(i);
		}
		return primes;
	}
	
	private boolean isPrime(long number) {
		long s = (long) Math.sqrt(number);
		
		for (int j=2; j<=s; j++) {
			if (number % j == 0)
				return false;
		}
		return true;
	}
}
