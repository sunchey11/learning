package tij.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class ByteBufferSample {
	public static void main(String[] args) {
		ByteBuffer b= ByteBuffer.allocate(10);
	
		System.out.println(b);
		
		System.out.println(b.remaining());
		
		ByteBuffer b2 = ByteBuffer.wrap("abcde".getBytes());
		System.out.println(new String(b2.array()));
		CharBuffer str = Charset.forName("utf-8").decode(b2);
		System.out.println(str.toString());
		System.out.println(b2);
		System.out.println(b2.remaining());
		
		while (b2.hasRemaining()){
			System.out.println(b2.position()+"="+ (char) b2.get()+",remainning="+b2.remaining());
		}
		
	}
}
