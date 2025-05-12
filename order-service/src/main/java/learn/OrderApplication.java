package learn;

import learn.service.OrderService;
import learn.service.impl.OrderServiceImpl;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication implements BeanFactoryAware {

    private static BeanFactory BEAN_FACTORY;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        OrderService orderService = BEAN_FACTORY.getBean(OrderService.class);
        orderService.createOrder(2);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BEAN_FACTORY = beanFactory;
    }
}
