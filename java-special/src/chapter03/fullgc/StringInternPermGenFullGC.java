package chapter03.fullgc;

import java.util.ArrayList;
import java.util.List;

public class StringInternPermGenFullGC {

	/**
	 * ���������������У�ע������JDK 1.6�����У���JDK 1.7�����еĽ������ȫ��ͬ
	 * ���з�ʽΪjava -XX:+PrintGCDetials -XX:PermSize10m -XX:MaxPermSize=10m chapter3.fullgc.StringInternPermGenFullGC
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<>();
		while (true) {
			list.add(("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" + i++).intern());
		}

	}
}
