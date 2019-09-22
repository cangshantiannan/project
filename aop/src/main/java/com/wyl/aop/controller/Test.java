/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package com.wyl.aop.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: Test
 * @Function: TODO
 * @Date: 2019/9/22 19:47
 */
@RestController
@RequestMapping(value = "/Test")
public class Test {
    @CrossOrigin
    @GetMapping(value = "/1")
    public String queryAllADAMJsonModuleInfo() {
        return "ok";
    }
}
