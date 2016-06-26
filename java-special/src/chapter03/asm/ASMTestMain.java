package chapter03.asm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class ASMTestMain {
	
	private final static DynamicClassLoader TEST_CLASS_LOADER = new DynamicClassLoader(
			(URLClassLoader) ASMTestMain.class.getClassLoader());

	public static void main(String []args) 
			throws ClassNotFoundException, IOException, InstantiationException, 
			       IllegalAccessException, IllegalArgumentException, SecurityException, 
			       InvocationTargetException, NoSuchMethodException {
		//在字节码增强前记录一个Class
		Class<?> beforeASMClass = TEST_CLASS_LOADER.loadClass("chapter03.asm.ForASMTestClass");
		
		//我们重新装载修改后的类
		TEST_CLASS_LOADER.defineClassByByteArray("chapter03.asm.ForASMTestClass", asmChangeClassCall());
		Class<?> afterASMClass = TEST_CLASS_LOADER.loadClass("chapter03.asm.ForASMTestClass");
		
		// 分别通过新老class创建对象
		Object beforeObject = beforeASMClass.newInstance();
		Object afterObject = afterASMClass.newInstance();
		
		//分表调用它们的代码
		System.out.println("**************** 原始的ForASMTestClass *******************");
		beforeASMClass.getMethod("display1").invoke(beforeObject);
		beforeASMClass.getMethod("display2").invoke(beforeObject);
		System.out.println("beforeObject 类加载器: " + beforeObject.getClass().getClassLoader());
		System.out.println("**************** 字节码增强的ForASMTestClass *******************");
		afterASMClass.getMethod("display1").invoke(afterObject);
		System.out.println("afterObject 类加载器: " + afterObject.getClass().getClassLoader());
		
		/**
		 *  Exception in thread "main" java.lang.NoSuchMethodException: chapter03.asm.ForASMTestClass.display2()
		 *  修改后的类调用display2(), 没有这个方法, 因为我们屏蔽了这个方法
		 */
		// afterASMClass.getMethod("display2").invoke(afterObject);
	}
	
	@SuppressWarnings("resource")
	private static byte[] asmChangeClassCall() throws IOException {
		ClassReader classReader = new ClassReader("chapter03.asm.ForASMTestClass");
		
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ASMClassModifyAdpter modifyAdpter = new ASMClassModifyAdpter(classWriter);
		classReader.accept(modifyAdpter, ClassReader.SKIP_DEBUG);
		//这里输出的字节码，可以用javap命令来查看哦
		byte[] bytes = classWriter.toByteArray();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		new FileOutputStream("/Users/wangpeng/Desktop/ForASMTestClass.class").getChannel().write(buffer);
		return bytes;
	}
}
