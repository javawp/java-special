package chapter05.atomic.reference;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceTest {

	/**
	 * ʹ�÷�ʽ������AtomicStampedReferenceһ��
	 * �������������Ӽ����������Ĺ�ϵ����������ֻ������״̬�������ڽ��ABA�����ʱ��Ҫ�����е�Ŀ��ͨ��ֻ������״̬
	 * ��ΪABA����Ҫ���ǵ���ģ���Ϊȷ����������һ����һ��״̬�����һ��״̬�󣬾Ͳ����ٱ仯������ֻ�Ǹ���Ҫ��ֻ������״̬
	 * һ�����ƣ���ɾ������ʧЧ�Ȳ��ɸ��õ�����������AtomicStampedReference���Ӿ��пɶ��ԣ���ΪAtomicStampedReference������һ�����޸ĵİ汾�źͼ�����
	 */
	public final static AtomicMarkableReference<String> ATOMIC_MARKABLE_REFERENCE = new AtomicMarkableReference<String>("abc", false);
	
	private final static Random RANDOM_OBJECT = new Random();
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch startCountDownLatch = new CountDownLatch(1);
		Thread[] threads = new Thread[20];
		for (int i = 0; i < 20; i++) {
			final int num = i;
			threads[i] = new Thread() {
				
				public void run() {
					String oldValue = ATOMIC_MARKABLE_REFERENCE.getReference();
					boolean marked = ATOMIC_MARKABLE_REFERENCE.isMarked();
					System.out.println("�����̣߳�" + num + ", ��ֵΪ: " + oldValue);
					try {
						startCountDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(RANDOM_OBJECT.nextInt() & 500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (ATOMIC_MARKABLE_REFERENCE.compareAndSet("abc", "abc2", false, true)) {
						System.out.println("�����̣߳�" + num + ", �һ�����������˶����޸ģ���ֵΪ: "
								+ ATOMIC_MARKABLE_REFERENCE.getReference() + ", �ɱ��Ϊ: [" + marked + "] , �±��Ϊ: ["
								+ ATOMIC_MARKABLE_REFERENCE.isMarked() + "]");
					}
				}
			};
			threads[i].start();
		}
		Thread.sleep(200);
		startCountDownLatch.countDown();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(RANDOM_OBJECT.nextInt() & 200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean marked = ATOMIC_MARKABLE_REFERENCE.isMarked();
				// ���������Ϊ�Ŀ����޸ı��, ����Ǹ�Ϊtrue�Ժ�, �Ͳ������޸ı��״̬��, �Ϳ��Ա���ABA������.
				while (!ATOMIC_MARKABLE_REFERENCE.compareAndSet(ATOMIC_MARKABLE_REFERENCE.getReference(), "abc", true, false));
				System.out.println("-----------> �Ѿ��޸�Ϊԭʼֵ��" + ATOMIC_MARKABLE_REFERENCE.getReference() + ", �ɱ��Ϊ: ["
						+ marked + "] �±��Ϊ: [" + ATOMIC_MARKABLE_REFERENCE.isMarked() + "]");
			}
		}.start();
	}
	
}
