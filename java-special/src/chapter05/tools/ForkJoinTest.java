package chapter05.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ʾ������: �����ֵ
 * ���Ҫ����������ӣ��뽫JDK����1.7���ϵİ汾
 * @author zhongyin.xy
 */
public class ForkJoinTest {

	private final static int SIZE = 10000000;

	private final static int FORK_NUM_PER = 5;

	private final static int PER_SIZE = 1000;

	private final static int[] TEST_INT_ARRAY = new int[SIZE];

	private final static ThreadLocal<DateFormat> date_format = new ThreadLocal<>();

	static {
		Random random = new Random();
		for (int i = 0; i < SIZE; i++) {
			TEST_INT_ARRAY[i] = random.nextInt(); // �������
		}
	}

	static class MaxComputer extends RecursiveTask<Integer> {

		private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

		private static final long serialVersionUID = 1L;

		private static final Random DEFAULT_RANDOM = new Random();

		private int startIndex;

		private int endIndex;

		public MaxComputer(int startIndex, int endIndex) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		public Integer compute() {
			int count = (endIndex - startIndex);
			if ((endIndex - startIndex) < PER_SIZE) {
				int v = TEST_INT_ARRAY[startIndex];
				for (int i = startIndex + 1; i < endIndex; i++) {
					// �������ͨ������������һ��������Ҫ����һ��ʱ�䣬���򿴲���̫��Ч��
					try {
						Thread.sleep(DEFAULT_RANDOM.nextInt() & 500);
						if (date_format.get() == null) {
							date_format.set(new SimpleDateFormat(DEFAULT_DATETIME_FORMAT));
							System.out.println(" [] �Ҵ�����[DateFormat]�̸߳���... [] " + Thread.currentThread().getName());
						}
						// ��ӡ��ǰ����ʱ��
						String dateTime = date_format.get().format(Calendar.getInstance().getTime());
						System.out.println(" [] ��ʼ���з��μ���..... [time] " + dateTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						/**
						 * Ŀǰ���Ǻ�����, ����Ӧ����ʲôʱ���Ƴ�, ��������Ƴ�, ���൱��ÿ�ζ���new������, ��ʲô����
						 * ������Ƴ�, ��ôÿ���̶߳������һ�� Thread --> ThreadLocalMap<ThreadLoad(ͬһ��), DateFormat>
						 */
						date_format.remove();
					}
					v = Math.max(v, TEST_INT_ARRAY[i]);
				}
				return v;
			}
			// ÿ�ηֽ�Ϊ���5��������
			MaxComputer[] maxComputer = new MaxComputer[5];
			int perConnt = count / FORK_NUM_PER; // ÿ�����������
			int min = startIndex, max = startIndex;
			for (int i = 0; i < FORK_NUM_PER; i++) { // ����ÿ������
				max += perConnt;
				max = Math.min(max, endIndex);
				maxComputer[i] = new MaxComputer(min, max);
				maxComputer[i].fork();
				min = max;
			}
			int result = Integer.MIN_VALUE;
			for (int i = 0; i < FORK_NUM_PER; i++) {
				result = Math.max(result, maxComputer[i].join());
			}
			return result;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		MaxComputer maxComputer = new MaxComputer(0, SIZE);
		pool.submit(maxComputer);
		System.out.println(maxComputer.get());
	}
}
