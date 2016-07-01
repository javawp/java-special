package chapter03.oom;

import java.util.ArrayList;
import java.util.List;

public class StringInternPermGenOOM {

	/**
	 * 请在命令行下运行
	 * 运行方式: -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseParallelOldGC
	 * 运行方式：java -XX:PermSize=2m -XX:MaxPermSize=2m chapter03.oom.StringInternPermGenOOM
	 * 
	 * @param args
	 */
	public static void main(String []args) {
		int i = 0;
		List<String>list = new ArrayList<String>();
		while(true) {
			list.add(("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" + i++).intern());
		}
	}
}
