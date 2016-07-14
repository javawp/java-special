package chapter05.atomic.updater;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {

	static class A {
		volatile int intValue = 100;
	}
	
	/**
	 * ����ֱ�ӷ��ʶ�Ӧ�ı����������޸ĺʹ���
	 * ������Ҫ�ڿɷ��ʵ������ڣ������private��������default�����Լ��Ǹ������protected���޷����ʵ�
	 * ��η��ʶ�������static���͵ı�������Ϊ�ڼ������Ե�ƫ������ʱ���޷����㣩��Ҳ������final���͵ı�������Ϊ�����޷��޸ģ�����������ͨ�ĳ�Ա����
	 * 
	 * ������˵���Ϻ�AtomicInteger����һ�£�Ψһ�������ǵ�һ��������Ҫ�����������ã�
	 * @see AtomicIntegerFieldUpdater#addAndGet(Object, int)
	 * @see AtomicIntegerFieldUpdater#compareAndSet(Object, int, int)
	 * @see AtomicIntegerFieldUpdater#decrementAndGet(Object)
	 * @see AtomicIntegerFieldUpdater#incrementAndGet(Object)
	 * 
	 * @see AtomicIntegerFieldUpdater#getAndAdd(Object, int)
	 * @see AtomicIntegerFieldUpdater#getAndDecrement(Object)
	 * @see AtomicIntegerFieldUpdater#getAndIncrement(Object)
	 * @see AtomicIntegerFieldUpdater#getAndSet(Object, int)
	 */
	public final static AtomicIntegerFieldUpdater<A> ATOMIC_INTEGER_UPDATER = AtomicIntegerFieldUpdater.newUpdater(A.class, "intValue");
	
	private final static Random RANDOM_OBJECT = new Random();
	
	public static void main(String[] args) throws InterruptedException {
		final A a = new A(); // ����ͬһ������
		final CountDownLatch startCountDownLatch = new CountDownLatch(1);
		Thread[] threads = new Thread[100];

		for (int i = 0; i < 100; i++) {
			final int num = i;
			threads[i] = new Thread() {
				public void run() {
					int oldValue = ATOMIC_INTEGER_UPDATER.get(a); // ��ȡ��ֵ
					System.out.println("�����̣߳�" + num + ", ��ֵΪ: " + oldValue);
					try {
						startCountDownLatch.await();
						Thread.sleep(RANDOM_OBJECT.nextInt() & 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (ATOMIC_INTEGER_UPDATER.compareAndSet(a, 100, 120)) {
						System.out.println("�����̣߳�" + num + " �ҶԶ�Ӧ��ֵ�����޸ģ���ֵΪ: [" + oldValue + "] , ��ֵΪ: ["
								+ ATOMIC_INTEGER_UPDATER.get(a) + "]");
					}
				}
			};
			threads[i].start();
		}
		Thread.sleep(200);
		startCountDownLatch.countDown();
		for (Thread thread : threads) {
			thread.join();
		}
		System.out.println("���ս��Ϊ��[" + ATOMIC_INTEGER_UPDATER.get(a) + "]");
	}
}
