package tij.concurrency.s7;

import java.util.concurrent.DelayQueue;

public class DelayQueueDemo2 {
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		queue.put(new DelayedTask(10000));
		DelayedTask take = queue.take();
		DelayedTask take2 =queue.take();
	}
}
