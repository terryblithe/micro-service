package com.terry.com.provider.controller;

import com.terry.com.provider.dao.UserRepository;
import com.terry.com.provider.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作用：
 *  1.测试服务实例的相关内容
 *  2.为后来的服务做提供
 * @author terry
 */
@RestController
public class UserController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserRepository userRepository;

    /**
     * 注：@GetMapping("/{id}")是Spring 4.3的新注解等价于：
     * @RequestMapping(value="/{id}", method=RequestMethod.GET)
     * 类似的注解还有@PostMapping等等
     * @param id
     * @return user信息
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }

    /**
     * 本地服务实例的信息
     * @return
     */
    @GetMapping("/instance-info")
    public ServiceInstance showInfo() {
        ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
        return localServiceInstance;
    }
}
