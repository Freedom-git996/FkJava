package base.reflect;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect
 * @version: 1.0
 */
public class URLClassLoaderTest {

    private static Connection conn;

    public static Connection getConn(String url, String user, String pass) throws Exception{
        if(conn == null){
            URL[] urls = {new URL("file:D:\\360Downloads\\maven-repository\\mysql\\mysql-connector-java\\5.1.47\\mysql-connector-java-5.1.47.jar")};
            URLClassLoader myClassLoader = new URLClassLoader(urls);
            Driver driver = (Driver)myClassLoader.loadClass("com.mysql.jdbc.Driver")
                    .getConstructor().newInstance();
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", pass);
            conn = driver.connect(url, props);
        }
        return conn;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getConn("jdbc:mysql://localhost:3306/orden?useSSL=false", "orden", "orden@123.com"));
    }
}
