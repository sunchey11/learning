package socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class Server {

	public static void main(String args[]) throws IOException {
		// 为了简单起见，所有的异常信息都往外抛
		int port = 1234;
		// 定义一个ServerSocket监听在端口8899上
		ServerSocket server = new ServerSocket(port);
		ServerSocketChannel channel = server.getChannel();
		System.out.println(channel);
		while (true) {
			// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
			Socket socket = server.accept();
			// 每接收到一个Socket就建立一个新的线程来处理它
			new Thread(new Task(socket)).start();
		}
	}

	/**
	 * 用来处理Socket请求的
	 */
	static class Task implements Runnable {

		private Socket socket;

		public Task(Socket socket) {
			this.socket = socket;
		}

		public void run() {

			try {

				handleSocket();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 跟客户端Socket进行通信
		 * 
		 * @throws Exception
		 */
		private void handleSocket() throws Exception {
			Writer writer = new OutputStreamWriter(socket.getOutputStream());
			writer.write("Hello Client.");
			writer.flush();
			writer.close();
			socket.close();
		}

	}

}