package chapter04.file;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CopyFileTestMain {
	
	public static void main(String []args) throws IOException {
//		copyFileByHeapByteBuffer();
//		copyFileByDirectByteBuffer();
		copyFileByMappedByteBuffer();
//		copyFileByChannel();
	}
	
	public static void copyFileByStreamTest() throws IOException {
		long start = System.currentTimeMillis();
		FileUtils.copyFile("f:/CentOS-6.7-x86_64-minimal.iso", "d:/CentOS-6.7-x86_64-minimal.iso");
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public static void copyFileByDirectByteBuffer() throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024);
		long start = System.currentTimeMillis();
		FileUtils.copyFileByByteBuffer("f:/CentOS-6.7-x86_64-minimal.iso", "d:/CentOS-6.7-x86_64-minimal.iso", byteBuffer, false);
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public static void copyFileByHeapByteBuffer() throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
		long start = System.currentTimeMillis();
		FileUtils.copyFileByByteBuffer("f:/CentOS-6.7-x86_64-minimal.iso", "d:/CentOS-6.7-x86_64-minimal.iso", byteBuffer, false);
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public static void copyFileByMappedByteBuffer() throws IOException {
		long start = System.currentTimeMillis();
		FileUtils.copyFileByMappedByteBuffer("f:/CentOS-6.7-x86_64-minimal.iso", "d:/CentOS-6.7-x86_64-minimal.iso");
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public static void copyFileByChannel() throws IOException {
		long start = System.currentTimeMillis();
		FileUtils.copyFileByChannel("f:/CentOS-6.7-x86_64-minimal.iso", "d:/CentOS-6.7-x86_64-minimal.iso");
		System.out.println(System.currentTimeMillis() - start);
	}

}
