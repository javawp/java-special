package chapter03.load;

public class ClassLoaderTest {  

    public static void main(String[] args) {  
        try {  
            /*ClassLoader loader = ClassLoaderTest.class.getClassLoader();  //���ClassLoaderTest������������� 
            while(loader != null) { 
                System.out.println(loader); 
                loader = loader.getParent();    //��ø������������� 
            } 
            System.out.println(loader);*/  

            String rootUrl = "/Users/wangpeng/GitHub/NIO/build/classes";  
			CustomClassLoader networkClassLoader = new CustomClassLoader(rootUrl);  
            String classname = "com.javawp.ReadChannel";  
            Class<?> clazz = networkClassLoader.loadClass(classname);  
            System.out.println(clazz.getClassLoader());  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
