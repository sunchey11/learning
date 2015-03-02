package tij.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class CharBufferDemo {
	public static void main(String[] args) {
		ByteBuffer b2= ByteBuffer.allocate(10);
		CharBuffer cb = b2.asCharBuffer();
		
		cb.put("Ëï");
		while (b2.hasRemaining()){
			System.out.println(b2.position()+"="+Byte.toString(b2.get())+",remainning="+b2.remaining());
		}
		b2.flip();

		
	}
}
