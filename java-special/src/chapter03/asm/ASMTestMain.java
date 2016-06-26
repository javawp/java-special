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
		//���ֽ�����ǿǰ��¼һ��Class
		Class<?> beforeASMClass = TEST_CLASS_LOADER.loadClass("chapter03.asm.ForASMTestClass");
		
		//��������װ���޸ĺ����
		TEST_CLASS_LOADER.defineClassByByteArray("chapter03.asm.ForASMTestClass", asmChangeClassCall());
		Class<?> afterASMClass = TEST_CLASS_LOADER.loadClass("chapter03.asm.ForASMTestClass");
		
		// �ֱ�ͨ������class��������
		Object beforeObject = beforeASMClass.newInstance();
		Object afterObject = afterASMClass.newInstance();
		
		//�ֱ�������ǵĴ���
		System.out.println("**************** ԭʼ��ForASMTestClass *******************");
		beforeASMClass.getMethod("display1").invoke(beforeObject);
		beforeASMClass.getMethod("display2").invoke(beforeObject);
		System.out.println("beforeObject �������: " + beforeObject.getClass().getClassLoader());
		System.out.println("**************** �ֽ�����ǿ��ForASMTestClass *******************");
		afterASMClass.getMethod("display1").invoke(afterObject);
		System.out.println("afterObject �������: " + afterObject.getClass().getClassLoader());
		
		/**
		 *  Exception in thread "main" java.lang.NoSuchMethodException: chapter03.asm.ForASMTestClass.display2()
		 *  �޸ĺ�������display2(), û���������, ��Ϊ�����������������
		 */
		// afterASMClass.getMethod("display2").invoke(afterObject);
	}
	
	@SuppressWarnings("resource")
	private static byte[] asmChangeClassCall() throws IOException {
		ClassReader classReader = new ClassReader("chapter03.asm.ForASMTestClass");
		
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ASMClassModifyAdpter modifyAdpter = new ASMClassModifyAdpter(classWriter);
		classReader.accept(modifyAdpter, ClassReader.SKIP_DEBUG);
		//����������ֽ��룬������javap�������鿴Ŷ
		byte[] bytes = classWriter.toByteArray();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		new FileOutputStream("/Users/wangpeng/Desktop/ForASMTestClass.class").getChannel().write(buffer);
		return bytes;
	}
}
