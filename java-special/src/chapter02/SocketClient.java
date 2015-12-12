package chapter02;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		SocketWrapper socket = new SocketWrapper(new Socket("localhost", 8888));
		try {
			System.out.println("�Ѿ������Ϸ�����,���ڿ����������ݿ�ʼͨѶ��");
			String sendMsg = scanner.nextLine();
			socket.writeLine(sendMsg); // ��������
			String recivedMsg = socket.readLine();
			while (!"close".equals(recivedMsg)) {
				System.out.println("===�����������ء�===> " + recivedMsg);
				sendMsg = scanner.nextLine(); // ����������Ϣ
				socket.writeLine(sendMsg); // ������Ϣ
				recivedMsg = socket.readLine();
			}
			System.out.println("���ǿͻ���,������!");
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}
