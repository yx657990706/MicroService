package com.yx.appcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/14
 * @time 2:42 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    private String name;
    private int price;
    private String author;
    private String publisher;
}
