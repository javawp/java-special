package chapter03.oom;

public class StackOverFlowTest {

	public static void main(String[] args) {
		new StackOverFlowTest().testStackOver(1);
		new StackOverFlowTest().testStackOver();
	}

	/**
	 * ���ݹ� 
	 *
	 * @����: ����
	 * @����ʱ��: 2016��7��1��-����11:45:32
	 */
	private void testStackOver() {
		testStackOver();
	}
	
	/**
	 * ͨ�����Ƶݹ����
	 * @param i
	 *
	 * @����: ����
	 * @����ʱ��: 2016��7��1��-����11:45:01
	 */
	public void testStackOver(int i) {
		if(i++ == 1) System.out.println("i++ �� ���ֱȽ�, �ȱȽ�, ���ۼ�.");
		if (i++ > 200) return;
		testStackOver(i);
	}
}
