package chapter03.oom;

public class StackOverFlowTest {

	public static void main(String[] args) {
		new StackOverFlowTest().testStackOver(1);
		new StackOverFlowTest().testStackOver();
	}

	/**
	 * 死递归 
	 *
	 * @作者: 王鹏
	 * @创建时间: 2016年7月1日-上午11:45:32
	 */
	private void testStackOver() {
		testStackOver();
	}
	
	/**
	 * 通过控制递归层数
	 * @param i
	 *
	 * @作者: 王鹏
	 * @创建时间: 2016年7月1日-上午11:45:01
	 */
	public void testStackOver(int i) {
		if(i++ == 1) System.out.println("i++ 与 数字比较, 先比较, 再累加.");
		if (i++ > 200) return;
		testStackOver(i);
	}
}
