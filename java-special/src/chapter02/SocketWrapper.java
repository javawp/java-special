package chapter02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWrapper {

	private Socket socket;

	private BufferedReader inputReader;

	private BufferedWriter outputWriter;

	public SocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		this.inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
		this.outputWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
	}

	public String readLine() throws IOException {
		return inputReader.readLine();
	}

	public void writeLine(String line) throws IOException {
		outputWriter.write(line + '\n');
		outputWriter.flush(); // 由于是Buffer,所以需要flush
	}

	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
