//: net/mindview/util/DaemonThreadPoolExecutor.java
package tij.concurrency;

import java.util.concurrent.*;

public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
	public DaemonThreadPoolExecutor() {
		super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(), new DaemonThreadFactory());
	}
	public static void main(String[] args) throws InterruptedException {
		DaemonThreadPoolExecutor exec = new DaemonThreadPoolExecutor();
		for (int i = 0; i < 5; i++)
			exec.execute(new LiftOff());
		TimeUnit.SECONDS.sleep(10);
	}
} // /:~
