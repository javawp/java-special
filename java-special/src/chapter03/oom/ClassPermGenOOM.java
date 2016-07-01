package chapter03.oom;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @描述: CGlib不断创建子类让Perm区域内存溢出
 * 
 * -XX:+PrintGCDetails -XX:PermSize=10m -XX:MaxPermSize=10m -XX:+TraceClassUnloading
 * 
 * @作者: 王鹏
 * @创建时间: 2016年7月1日-上午9:40:59
 * @版本: 1.0
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
