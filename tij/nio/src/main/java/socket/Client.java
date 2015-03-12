package socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import org.apache.commons.io.IOUtils;

public class Client {

	public static void main(String args[]) throws Exception {
		// 为了简单起见，所有的异常都直接往外抛
		String host = "127.0.0.1"; // 要连接的服务端IP地址
		int port = 1234; // 要连接的服务端对应的监听端口
		// 与服务端建立连接
		Socket client = new Socket(host, port);
		SocketChannel channel = client.getChannel();
		System.out.println(channel);
		// 写完以后进行读操作
		InputStream inputStream = client.getInputStream();
		
		int b;
		b = inputStream.read();
		while(b!=-1){
			System.out.print((char)b);
			b = inputStream.read();
		}
		
//		String string = IOUtils.toString(inputStream);
//		System.out.println(string);
		
		client.close();
	}

}