package learn.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring ioc容器组件
 *
 */
@Component
public class SpringApplicationContext {

    /**
     * spring容器
     */
    private ApplicationContext context;

    /**
     * 构造函数
     *
     * @param context spring容器
     */
    public SpringApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 获取bean
     *
     * @param clazz bean类型
     * @return bean实例
     */
    public <T> T getBean(Class<? extends T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 获取bean
     *
     * @param beanName bean名称
     * @return bean实例
     */
    public Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
