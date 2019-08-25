package com.yx.appconsumer.demo.controller;

import com.yx.appconsumer.demo.service.HelloService;
import com.yx.appcoreservicer.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2018/9/11
 * @time 下午4:05
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@RestController
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;
//
//    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
//    public String helloController() {
//        return restTemplate.getForEntity("http://APP-PROVIDER/hello", String.class).getBody();
//    }

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/ribbon-consumer",method = RequestMethod.GET)
    public String helloController() {
        return helloService.hello();
    }

    @RequestMapping("/book1")
    public Book book1() {
        //返回响应码、contentType、contentLength、响应消息体等
        ResponseEntity<Book> responseEntity = restTemplate.getForEntity("http://APP-PROVIDER/getbook1", Book.class);
        return responseEntity.getBody();
    }

    @RequestMapping("/book2")
    public Book book2() {
        //只关注返回的消息体的内容，对其他信息都不关注
        Book book = restTemplate.getForObject("http://APP-PROVIDER/getbook1", Book.class);
        return book;
    }

    @RequestMapping("/book3")
    public Book book3() {
        Book book = new Book();
        book.setName("红楼梦");
        ResponseEntity<Book> responseEntity = restTemplate.postForEntity("http://APP-PROVIDER/getbook2", book, Book.class);
        return responseEntity.getBody();
    }
}
