package chapter05.pool;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @����: ģ����Դ�����õ����
 * 
 * @����: ����
 * @����ʱ��: 2016��7��16��-����11:46:46
 * @�汾: 1.0
 */
public class SingleThreadPool {

	private static final int SLEEP = 1000;

	public static void main(String[] args) {

		final Random random = new Random();

		ExecutorService threadPool = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "�ʼ�ר���̳߳� [" + Thread.currentThread().getId() + "]");
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
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] ��С�ⷢ��һ���ʼ�.");
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
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "] ��С���һ���ʼ�.");
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
						Thread.currentThread().getName() + "[" + Thread.currentThread().getId() + "]" + " ��С������һ���ʼ�.");
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
