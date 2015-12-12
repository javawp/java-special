package chapter02;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("�˿��Ѵ�Ϊ8888,��ʼ׼����������");
		SocketWrapper socket = null;
		try {
			socket = new SocketWrapper(serverSocket.accept());
			String line = socket.readLine();
			while (!"bye".equals(line)) {
				System.out.println("�ͻ��˴�������: " + line);
				socket.writeLine("�ҽ��յ��������: " + line);
				line = socket.readLine();
			}
			socket.writeLine("close");
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}
