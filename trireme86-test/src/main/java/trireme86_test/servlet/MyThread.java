package trireme86_test.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyThread extends Thread {
	public MyThread(HttpServletRequest req, HttpServletResponse resp){
		
	}
	@Override
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.run();
	}
}
