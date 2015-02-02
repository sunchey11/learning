package anders;

public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			float x = 1.0f;
			for (int j = 0; j < 1000000; j++) {
				x = x % j;
			}
			Thread.sleep(100);
		}

	}

}
