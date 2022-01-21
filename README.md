# 1、Java SPI机制 ServiceLoader的基本使用
 -  <big>**Java SPI 实际上是“基于接口的编程＋策略模式＋配置文件”组合实现的动态加载机制，提供了通过interface寻找implement的方法。类似于IOC的思想，将装配的控制权移到程序之外，从而实现解耦。**</big>
 -  <big>**适应场景：比如两个module，app依赖basic_live，但这时basic_live又用到了app模块中的服务。**</big>

# 2、方式一
 - <big>**在调用module中创建一个接口文件ContentService**</big>
 - <big>**在具体实现的module中的main目录（Java同级）下建立resources/META-INF/services/，因为ServiceLoader中会访问到（查看ServiceLoader.PREFIX）**</big>
 - <big>**在services文件夹中创建文件，以接口全名命名**</big>
 - <big>**在具体实现的module中创建类来实现接口功能ContentServiceImpl，然后把全类名填到上面创建的文件中**</big>

# 3、方式二
 - <big>**在方式一中，不仅要创建各种路径、文件，如果写错，所以Google发布了一个库autoservice帮助大家**</big>
 - <big>**在具体实现module中添加依赖，kotlin的可以用kpt**</big>
   
 - ```
   implementation 'com.google.auto.service:auto-service:1.0.1'
   annotationProcessor 'com.google.auto.service:auto-service:1.0.1'
   
   kapt 'com.google.auto.service:auto-service:1.0.1'
   ```
 - <big>**实现接口，并加上@AutoService**</big>
- ```
  @AutoService(ContentService.class)
  public class ContentServiceImpl implements ContentService {
        @Override
        public String getTitle() {
             return "the title from app module";
         }
 }
   ```
# 4、使用
 - <big>**在ServiceFactory中加了缓存,通过
 ContentService service = ServiceFactory.getInstance().getService(ContentService.class)获取**</big>

```
    public class ServiceFactory {

    private static class SingleTonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingleTonHolder.INSTANCE;
    }
    private final ArrayMap<Class, ServiceLoader> loaderMap = new ArrayMap<>();
    private final ArrayMap<Class, Object> serviceMap = new ArrayMap<>();
    private ServiceFactory() {

    }
    @Nullable
    public <T> T getService(Class<T> clazz) {
        Object o = serviceMap.get(clazz);
        if (o != null && isInterface(o.getClass(),clazz.getName())) {
            return (T) o;
        }
        ServiceLoader serviceLoader = loaderMap.get(clazz);
        if (serviceLoader == null) {
            serviceLoader = ServiceLoader.load(clazz);
            loaderMap.put(clazz, serviceLoader);
        }
        if (serviceLoader != null && serviceLoader.iterator().hasNext()) {
            T next = (T) serviceLoader.iterator().next();
            serviceMap.put(clazz, next);
            return next;
        }
        return null;
    }
    public boolean isInterface(Class c, String szInterface) {
        Class[] face = c.getInterfaces();
        for (Class aClass : face) {
            if (aClass.getName().equals(szInterface)) {
                return true;
            } else {
                Class[] face1 = aClass.getInterfaces();
                for (Class value : face1) {
                    if (value.getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(value, szInterface)) {
                        return true;
                    }
                }
            }
        }
        if (null != c.getSuperclass()) {
            return isInterface(c.getSuperclass(), szInterface);
        }
        return false;
    }
 }

```
