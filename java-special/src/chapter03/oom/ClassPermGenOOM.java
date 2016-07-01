package chapter03.oom;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @����: CGlib���ϴ���������Perm�����ڴ����
 * 
 * -XX:+PrintGCDetails -XX:PermSize=10m -XX:MaxPermSize=10m -XX:+TraceClassUnloading
 * 
 * @����: ����
 * @����ʱ��: 2016��7��1��-����9:40:59
 * @�汾: 1.0
 */
public class ClassPermGenOOM {

	private static URLClassLoader parentClassLoader;

	static {
		parentClassLoader = (URLClassLoader) ClassPermGenOOM.class.getClassLoader();
	}

	public static Object createProxy(Class<?> targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setClassLoader(new URLClassLoader(parentClassLoader.getURLs(), parentClassLoader));
		enhancer.setUseCache(false);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object object, Method method, Object[] args,
					MethodProxy methodProxy) throws Throwable {
				return methodProxy.invokeSuper(object, args);
			}});
		return enhancer.create();
	}

	public static void main(String[] args) {
		while (true) {
			createProxy(ClassPermGenOOM.class);
		}
	}
}
