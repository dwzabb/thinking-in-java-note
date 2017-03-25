package ch14typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        Subject proxyObject = (Subject)Proxy.newProxyInstance(
                RealSubject.class.getClassLoader(),
                RealSubject.class.getInterfaces(),
                proxyHandler);
        proxyObject.request();
    }
}

/**
 * 接口
 */
interface Subject{
    String request();
}

/**
 * 委托类
 */
class RealSubject implements Subject{
    public String request(){
        System.out.println("====RealSubject Request====");
        return "RealSubject.request()";
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
