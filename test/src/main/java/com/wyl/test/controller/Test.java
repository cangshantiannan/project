/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package com.wyl.test.controller;

import com.wyl.aop.annotation.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wyl.test.conf.ExampleService;

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

    @Autowired
    private ExampleService exampleService;

    @GetMapping(value = "/1/{word}")
    @RequestInfo
    public String queryAllADAMJsonModuleInfo(@PathVariable String word) {
        return exampleService.wrap(word);
    }
}
