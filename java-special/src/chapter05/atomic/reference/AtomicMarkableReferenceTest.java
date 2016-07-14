package chapter05.atomic.reference;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceTest {

	/**
	 * 使用方式基本和AtomicStampedReference一致
	 * 区别在于他更加简单描述是与否的关系，而它本身只有两种状态，所以在解决ABA问题的时候，要求运行的目标通常只有两个状态
	 * 因为ABA本身要求是单向的（人为确定），所以一旦从一种状态变成另一种状态后，就不能再变化回来，只是该类要求只有两个状态
	 * 一般类似：已删除、已失效等不可复用的内容用它比AtomicStampedReference更加具有可读性，因为AtomicStampedReference仅仅是一个类修改的版本号和计数器
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
					System.out.println("我是线程：" + num + ", 旧值为: " + oldValue);
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
						System.out.println("我是线程：" + num + ", 我获得了锁进行了对象修改！新值为: "
								+ ATOMIC_MARKABLE_REFERENCE.getReference() + ", 旧标记为: [" + marked + "] , 新标记为: ["
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
				// 这里可以人为的控制修改标记, 当标记改为true以后, 就不允许修改标记状态了, 就可以避免ABA的问题.
				while (!ATOMIC_MARKABLE_REFERENCE.compareAndSet(ATOMIC_MARKABLE_REFERENCE.getReference(), "abc", true, false));
				System.out.println("-----------> 已经修改为原始值！" + ATOMIC_MARKABLE_REFERENCE.getReference() + ", 旧标记为: ["
						+ marked + "] 新标记为: [" + ATOMIC_MARKABLE_REFERENCE.isMarked() + "]");
			}
		}.start();
	}
	
}
