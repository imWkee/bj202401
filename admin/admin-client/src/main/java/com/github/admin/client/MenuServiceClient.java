package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

/**
 * @Time: 2024/1/12
 * @Author: Wangke
 * @Description:
 * @FileName: MenuServiceClient.java
 * @Software: IntelliJ IDEA
 **/

@RestController
@FeignClient(name = "admin-server", url = "127.0.0.1:8082")
public interface MenuServiceClient {

    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId") Long userId);

}
