package chapter03.inst.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		//���װ�ص�����,ͬ�����������װ��
		System.out.println("load class:" + className);
		//ֻ��ָ�������װ��
		if("chapter03/asm/ForASMTestClass".equals(className)) {
			try {
				CtClass ctClass = ClassPool.getDefault().get(className.replace('/', '.'));
				CtMethod ctMethod = ctClass.getDeclaredMethod("display1");
				ctMethod.insertBefore(
						"name=\"����name�������javassist��Ŷ��\";" +
						"value=\"����value��\";" +
						"System.out.println(\"���Ǽӽ�ȥ��Ŷ��������\" + name);"
				);
				ctMethod.insertAfter("System.out.println(value);");
				return ctClass.toBytecode();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return classfileBuffer;
	}
}
