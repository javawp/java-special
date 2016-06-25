package chapter03.load;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @����: ����class��ClassLoader 
 * 
 * @����: ����
 * @����ʱ��: 2016��6��25��-����10:19:01
 * @�汾: 1.0
 */
public class CustomClassLoader extends ClassLoader {

	private String rootUrl;

	public CustomClassLoader(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = this.findLoadedClass(name); // �жϸ����Ƿ��Ѽ���
		if (clazz == null) { // �������Ƿ��ѱ����ع�
			byte[] classData = getClassData(name); // ������Ķ���������,��ø�class�ļ����ֽ�������
			if (classData == null) {
				throw new ClassNotFoundException();
			}
			clazz = defineClass(name, classData, 0, classData.length); // ��class���ֽ�������ת����Class���ʵ��
		}
		return clazz;
	}

	private byte[] getClassData(String name) {
		String path = classNameToPath(name);
		try (FileInputStream is = new FileInputStream(path)) {
			int len = -1;
			// ��ȡͨ��
			FileChannel fc = is.getChannel();
			// ��һ���Ǵ���������
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);
			// �����Ҫ�����ݴ�ͨ��������������
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = fc.read(buffer)) != -1) {
				baos.write(buffer.array(), 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String name) {
		return rootUrl + "/" + name.replace(".", "/") + ".class";
	}

}