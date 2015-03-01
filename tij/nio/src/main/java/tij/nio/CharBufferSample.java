package tij.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class CharBufferSample {
	public static void main(String[] args) {
		ByteBuffer b2 = ByteBuffer.wrap("abcde".getBytes());
		b2.position(3);
		CharBuffer charBuffer = b2.asCharBuffer();
		System.out.println(charBuffer.position());
		System.out.println(charBuffer.limit());
		System.out.println(charBuffer.capacity());
		System.out.println(charBuffer);
	}
}
