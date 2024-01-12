package com.github.admin.client;

import com.github.admin.common.domain.Role;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@FeignClient(name="admin-server", url = "127.0.0.1:8082")
public interface RoleServiceClient {

    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/findRolePermissionsByUserId/{userId}")
    Result<Set<Role>> findRolePermissionsByUserId(@PathVariable("userId") Long userId);
}
