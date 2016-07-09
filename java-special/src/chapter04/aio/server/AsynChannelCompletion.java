package chapter04.aio.server;

import static chapter04.socket.Commons.DEFAULT_MESSAGE_CHARSET;
import static chapter04.socket.Commons.closeStreams;
import static chapter04.socket.Commons.logInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;

public class AsynChannelCompletion implements CompletionHandler<Integer, Object>{

	private ByteBuffer byteBuffer;
	private AsynchronousSocketChannel asynchronousSocketChannel;
	
	public AsynChannelCompletion(ByteBuffer byteBuffer, AsynchronousSocketChannel asynchronousSocketChannel) {
		this.byteBuffer = byteBuffer;
		this.asynchronousSocketChannel = asynchronousSocketChannel;
	}

	@Override
	public void completed(Integer result, Object attachment) {
		if (result > 0) {
			try {
				byte[] bytes = new byte[result];
				byteBuffer.flip();
				byteBuffer.get(bytes);
				logInfo("[" + result + "] ---> " + Arrays.toString(bytes));
				byteBuffer.clear();
				if (bytes.length == 1) {
					logInfo("sendMsg 来了, 类型是: " + bytes[0]);
				} else if (bytes.length == 4) {
					logInfo("接受到来自客户端传来message信息, 长度是: " + readInt(bytes));
				} else {
					logInfo("接受到来自客户端传来message信息：" + new String(bytes, DEFAULT_MESSAGE_CHARSET));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			asynchronousSocketChannel.read(byteBuffer, null, this);
		} else {
			closeStreams(asynchronousSocketChannel);
		}

	}

	@Override
	public void failed(Throwable exc, Object attachment) {
		exc.printStackTrace();
	}

	private int readInt(byte[] bytes) throws IOException {
		byte b0 = bytes[0];
		byte b1 = bytes[1];
		byte b2 = bytes[2];
		byte b3 = bytes[3];
		return ((b0 << 24) + (b1 << 16) + (b2 << 8) + (b3 << 0));
	}

}
