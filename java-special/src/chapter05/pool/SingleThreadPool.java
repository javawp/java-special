package chapter05.pool;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @描述: 模拟资源不够用的情况
 * 
 * @作者: 王鹏
 * @创建时间: 2016年7月16日-上午11:46:46
 * @版本: 1.0
 */
public class SingleThreadPool {

	private static final int SLEEP = 1000;

	public static void main(String[] args) {

		final Random random = new Random();

		ExecutorService threadPool = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "邮件专用线程池 [" + Thread.currentThread().getId() + "]");
			}
		});

		Runnable sendEmail0 = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(random.nextInt() & SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] 给小吴发送一封邮件.");
			}
		};

		Runnable sendEmail1 = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(random.nextInt() & SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] 给小李发送一封邮件.");
			}
		};

		Runnable sendEmail2 = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(random.nextInt() & SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
