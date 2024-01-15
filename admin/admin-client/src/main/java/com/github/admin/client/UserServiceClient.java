package com.github.admin.client;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RestController
@FeignClient(name="admin-server", url = "127.0.0.1:8082")
public interface UserServiceClient {

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("userName") String userName);

    @PostMapping("/findUserByPage")
    Result<DataPage<User>> findUserByPage(@RequestBody UserRequest userRequest);


}
