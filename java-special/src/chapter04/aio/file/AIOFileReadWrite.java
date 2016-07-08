package chapter04.aio.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;


public class AIOFileReadWrite {

	public static void main(String []args) throws IOException {
		AsynchronousFileChannel readChannel = AsynchronousFileChannel.open(Paths.get("/Users/wangpeng/Downloads/hibernate_reference.PDF"));
		//AsynchronousFileChannel.open(file, options, executor, attrs)
		FileChannel writeChannel = new FileOutputStream("/Users/wangpeng/Documents/dir/hibernate_reference.PDF").getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		FileReadCompletion completion = new FileReadCompletion(byteBuffer , readChannel);
		readChannel.read(byteBuffer, 0l , writeChannel , completion);
		System.in.read();//让程序暂停在这里，否则会直接退出
	}
}
