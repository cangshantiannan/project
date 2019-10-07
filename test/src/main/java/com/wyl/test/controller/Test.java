/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package com.wyl.test.controller;

import com.wyl.aop.annotation.RequestInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: Test
 * @Function: TODO
 * @Date: 2019/9/22 19:47
 */
@RestController
@RequestMapping(value = "/Test")
@CrossOrigin
public class Test {
    @GetMapping(value = "/1")
    @RequestInfo
    public String queryAllADAMJsonModuleInfo() {
        return "ok";
    }
}
