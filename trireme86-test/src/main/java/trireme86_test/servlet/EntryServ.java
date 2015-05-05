package trireme86_test.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntryServ extends HttpServlet {
	private ExecutorService pool = Executors.newFixedThreadPool(5);
	/** 
     *  
     */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Thread t1 = new MyThread(req, resp);
		pool.execute(t1);
		
	}

}