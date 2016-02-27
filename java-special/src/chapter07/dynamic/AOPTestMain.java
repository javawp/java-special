package chapter07.dynamic;

import java.lang.reflect.Method;

public class AOPTestMain {

	public static void main(String []args) {
		try {
			HelloInterface hello = BeanFactory.getBean("chapter07.dynamic.HelloImpl" , HelloInterface.class);
			hello.setInfo("xieyuooo", "xiaopang");
			//hello.getInfos1();
			//hello.getInfos2();
			//hello.display();
			
			// jvm ��̬������ڽӿڵķ������������þ���ʵ���෽����
			Method method = HelloInterface.class.getDeclaredMethod("setInfo", String.class, String.class);
			method.invoke(new HelloImpl(), "aaa", "bbb");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
