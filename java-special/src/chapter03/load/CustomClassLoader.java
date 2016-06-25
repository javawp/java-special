package chapter03.load;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @描述: 加载class的ClassLoader 
 * 
 * @作者: 王鹏
 * @创建时间: 2016年6月25日-下午10:19:01
 * @版本: 1.0
 */
public class CustomClassLoader extends ClassLoader {

	private String rootUrl;

	public CustomClassLoader(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = this.findLoadedClass(name); // 判断父类是否已加载
		if (clazz == null) { // 检查该类是否已被加载过
			byte[] classData = getClassData(name); // 根据类的二进制名称,获得该class文件的字节码数组
			if (classData == null) {
				throw new ClassNotFoundException();
			}
			clazz = defineClass(name, classData, 0, classData.length); // 将class的字节码数组转换成Class类的实例
		}
		return clazz;
	}

	private byte[] getClassData(String name) {
		String path = classNameToPath(name);
		try (FileInputStream is = new FileInputStream(path)) {
			int len = -1;
			// 获取通道
			FileChannel fc = is.getChannel();
			// 下一步是创建缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);
			// 最后，需要将数据从通道读到缓冲区中
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