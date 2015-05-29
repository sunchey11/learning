package tij.concurrency.s5;

public class Basic {
	private Object o = new Object();

	public void f() {
		System.out.println(Thread.holdsLock(o));
		synchronized (this) {
			synchronized (o) {
				try {
					System.out.println(Thread.holdsLock(o));
					o.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(Thread.holdsLock(o));

	}

	public static void main(String[] args) {
		new Basic().f();
	}

}
