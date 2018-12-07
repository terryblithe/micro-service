package com.terry.cloud.consumer.feign.hystrix.client;

import com.terry.cloud.consumer.feign.hystrix.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用@FeignClient("microservice-provider-user")注解绑定microservice-provider-user服务，还可以使用url参数指定一个URL.
 * fallback属性，指定fallback类
 * @author terry
 */
@FeignClient(name = "microservice-provider-user", fallback = UserFeignHystrixClient.HystrixClientFallback.class)
public interface UserFeignHystrixClient {

    @RequestMapping("/{id}")
    public User findByIdFeign(@RequestParam("id") Long id);

    /**
     * 这边采用了和SpringCloud官方文档相同的做法，将fallback类作为内部类放入feign的接口中，当然也可以单独写一个fallback类
     * @author terry
     */
    @Component
    static class HystrixClientFallback implements UserFeignHystrixClient {

        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallback.class);

        /**
         * hystrix fallback 方法
         * @param id
         * @return 默认的用户
         */
        @Override
        public User findByIdFeign(Long id) {

            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
            User user = new User();
            user.setId(-2L);
            user.setUsername("default username");
            user.setAge(0);

            return user;
        }
    }

}
