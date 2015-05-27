package tij.concurrency;

import java.util.concurrent.TimeUnit;

//: concurrency/Joining.java
// Understanding join().

class Sleeper extends Thread {
	private int duration;

	public Sleeper(String name, int sleepTime) {
		super(name);
		duration = sleepTime;
		start();
	}

	public void run() {
		try {
			sleep(duration);
		} catch (InterruptedException e) {
			print(getName() + " was interrupted. " + "isInterrupted(): "
					+ isInterrupted());
			return;
		}
		print(getName() + " has awakened");
	}

	private void print(String string) {
		System.out.println(string);

	}
}

class Joiner extends Thread {
	private Sleeper sleeper;

	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}

	public void run() {
		try {
			sleeper.join();
		} catch (InterruptedException e) {
			print("Interrupted");
		}
		print(getName() + " join completed");
	}

	private void print(String string) {
		System.out.println(string);

	}
}

public class Joining {
	public static void main(String[] args) throws InterruptedException {
		Sleeper grumpy = new Sleeper("Grumpy", 1500);
		Joiner doc = new Joiner("Doc", grumpy);
		grumpy.interrupt();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("grumpy.isInterrupted()="+grumpy.isInterrupted());
		System.out.println("doc.isInterrupted()="+doc.isInterrupted());
	}
} /*
 * Output: Grumpy was interrupted. isInterrupted(): false Doc join completed
 * Sleepy has awakened Dopey join completed
 */// :~
