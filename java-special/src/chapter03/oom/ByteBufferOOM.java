package chapter03.oom;

import java.nio.ByteBuffer;

import sun.nio.ch.DirectBuffer;

public class ByteBufferOOM {

	// -XX:MaxDirectMemorySize=256m
	public static void main(String[] args) {
		// ByteBuffer.allocateDirect(257 * 1024 * 1024);
		
//		ByteBuffer.allocateDirect(256 * 1024 * 1024);
//		ByteBuffer.allocateDirect(1024 * 1024);
		
		DirectBuffer byteBuffer = (DirectBuffer) ByteBuffer.allocateDirect(256 * 1024 * 1024);
//		byteBuffer = null;
		((DirectBuffer) byteBuffer).cleaner().clean();
		ByteBuffer.allocateDirect(1024 * 1024);
	}
}
