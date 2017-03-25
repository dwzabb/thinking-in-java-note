package ch14typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);

//        Class clazz = Proxy.getProxyClass(
//                RealSubject.class.getClassLoader(),
//                RealSubject.class.getInterfaces());
//        System.out.println("Clazz " + clazz);
//        System.out.println("Clazz methods " + clazz.getMethods());
//        System.out.println("Clazz super class " + clazz.getSuperclass());

        Subject proxyObject = (Subject)Proxy.newProxyInstance(
                RealSubject.class.getClassLoader(),
                RealSubject.class.getInterfaces(),
                proxyHandler);
        proxyObject.request();

        proxyObject.anotherRequest();
    }
}

/**
 * 接口
 */
interface Subject{
    String request();
    void anotherRequest();
}

/**
 * 委托类
 */
class RealSubject implements Subject{
    @Override
    public String request(){
        System.out.println("====RealSubject request====");
        return "RealSubject.request()";
    }

    @Override
    public void anotherRequest() {
        System.out.println("====RealSubject anotherRequest====");
    }
}

/**
 * 代理类的调用处理器
 */
class ProxyHandler implements InvocationHandler {

    private Subject subject;
    public ProxyHandler(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before " + method);
        Object result = method.invoke(subject, args);
        System.out.println("After " + method + ", Result " + result);
        return result;
    }
}
