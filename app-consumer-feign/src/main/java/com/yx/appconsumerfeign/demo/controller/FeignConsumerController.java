package com.yx.appconsumerfeign.demo.controller;

import com.yx.appconsumerfeign.demo.service.HelloService;
import com.yx.commonsmodel.myModel.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2018/9/12
 * @time 下午4:37
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RestController
public class FeignConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping("/hello/a")
    public String helloa() {
        return "hello/a -----";
    }

    @RequestMapping("/api-a/hello")
    public String hellob() {
        return "hello/a-1 -----";
    }

    @RequestMapping("/api-c/hello/c")
    public String helloc() {
        return "hello/c -----";
    }

    @RequestMapping("/hello1")
    public String hello1() {
        return helloService.hello("张三");
    }

    @RequestMapping(value = "/hello2")
    public Book hello2() throws UnsupportedEncodingException {
        Book book = helloService.hello(URLEncoder.encode("三国演义","UTF-8"), URLEncoder.encode("罗贯中","UTF-8"), 33);
        System.out.println(book);
        return book;
    }

    @RequestMapping("/hello3")
    public String hello3() {
        Book book = new Book();
        book.setName("红楼梦");
        book.setPrice(44);
        book.setAuthor("曹雪芹");
        return helloService.hello(book);
    }

}