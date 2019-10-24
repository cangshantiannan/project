/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package until;

import ch.ethz.ssh2.StreamGobbler;
import lombok.NonNull;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public enum IOTools {
    /**
     *工具对象
     */
    TOOLS;

    /**
     * @return java.util.List<java.lang.String>
     * @Description 将输入流转为list
     * @Date 2019/10/22 23:15
     * @Author wangyl
     * @Version V1.0
     */
    public List<String> getInputStreamToList(InputStream inputStream) throws IOException {
        InputStream stdout = new StreamGobbler(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
        List<String> res = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            res.add(line);
        }
        return res;
    }

    /**
     * @return void
     * @Description 将List以换行的形式输出
     * @Date 2019/10/22 23:58
     * @Author wangyl
     * @Version V1.0
     */
    public <T> void listOutFomart(List<T> list) {
        list.stream().forEach(e -> {
            System.out.println(e);
        });
    }

    /**
     * @Description 输出一个对象的属性以及属性的值
     * @param object
     * @return void
     * @Date 2019/10/25 0:49
     * @Author wangyl
     * @Version V1.0
     */
    public void printClassObjectInfo(@NonNull Object object) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method getMethod = pd.getReadMethod();
            System.out.println("[" + field.getName() + "] is [" + getMethod.invoke(object) + "]");
        }
    }

    /**
     * @Description 将一个异常转为String
     * @return java.lang.String
     * @Date 2019/10/24 23:41
     * @Author wangyl
     * @Version V1.0
     */
    public String getExceptionToString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
