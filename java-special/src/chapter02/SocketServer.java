package chapter02;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("端口已打开为8888,开始准备接收数据");
		SocketWrapper socket = null;
		try {
			socket = new SocketWrapper(serverSocket.accept());
			String line = socket.readLine();
			while (!"bye".equals(line)) {
				System.out.println("客户端传来数据: " + line);
				socket.writeLine("我接收到你的数据: " + line);
				line = socket.readLine();
			}
			socket.writeLine("close");
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}
