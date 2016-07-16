package chapter05.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class FixedThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService threadPool = Executors.newFixedThreadPool(10, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "�ʼ�ר���̳߳�");
			}
		});

		Runnable sendEmail0 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " [] ��С�ⷢ��һ���ʼ�.");
			}
		};

		Runnable sendEmail1 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " [] ��С���һ���ʼ�.");
			}
		};

		Runnable sendEmail2 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " [] ��С������һ���ʼ�.");
			}
		};

		threadPool.execute(sendEmail0);
		System.out.println("void execute(Runnable command) [] û�з���ֵ.");

		Future<?> future = threadPool.submit(sendEmail1);
		Object result = future.get();
		System.out.println("Future<?> submit(Runnable task) [] ����ֵ: " + result);

		Future<Object> future2 = threadPool.submit(sendEmail2, null);
		Object result2 = future2.get();
		System.out.println("<T> Future<T> submit(Runnable task, T result) [] " + result2);
	}
}
