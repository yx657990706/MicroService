package com.yx.appconsumerfeign.demo.service;

import com.yx.appconsumerfeign.demo.service.impl.HelloServiceHystric;
import com.yx.commonsmodel.myModel.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2018/9/12
 * @time 下午4:35
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@FeignClient(value = "app-provider",fallback=HelloServiceHystric.class)//@FeignClient注解来指定服务名进而绑定服务
@Service
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    Book hello(@RequestHeader("name") String name, @RequestHeader("author") String author, @RequestHeader("price") Integer price);

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    String hello(@RequestBody Book book);
}
