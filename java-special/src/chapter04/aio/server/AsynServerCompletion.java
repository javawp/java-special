package chapter04.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsynServerCompletion implements CompletionHandler<AsynchronousSocketChannel, Object> {

	private ByteBuffer byteBuffer;

	public AsynServerCompletion(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	@Override
	public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Object attachment) {
		asynchronousSocketChannel.read(byteBuffer, attachment,
				new AsynChannelCompletion(byteBuffer, asynchronousSocketChannel));
	}

	@Override
	public void failed(Throwable exc, Object attachment) {
		exc.printStackTrace();
	}

}
