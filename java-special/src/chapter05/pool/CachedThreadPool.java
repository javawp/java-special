package chapter05.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CachedThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService threadPool = Executors.newCachedThreadPool(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "邮件专用线程池 [" + Thread.currentThread().getId() + "]");
			}
		});

		Runnable sendEmail0 = new Runnable() {
			@Override
			public void run() {
				System.out.println(
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] 给小吴发送一封邮件.");
			}
		};

		Runnable sendEmail1 = new Runnable() {
			@Override
			public void run() {
				System.out.println(
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] 给小李发送一封邮件.");
			}
		};

		Runnable sendEmail2 = new Runnable() {
			@Override
			public void run() {
				System.out.println(
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "]" + " 给小王发送一封邮件.");
			}
		};

		List<Runnable> list = Arrays.asList(sendEmail0, sendEmail1, sendEmail2);
		Runnable[] sendEmail = list.toArray(new Runnable[3]);

		for (int i = 0; i < 1000; i++) {
			int j = i % 3;
			threadPool.execute(sendEmail[j]);
		}

	}
}
