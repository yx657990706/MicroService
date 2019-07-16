package com.yx.apigatewayserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/15
 * @time 2:17 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class PermisFilter extends ZuulFilter {

    //过滤器的类型决定了过滤器在哪个生命周期执行
    @Override
    public String filterType() {
//        return null;
        //pre表示在路由之前执行过滤器，其他可选值还有post（路由后）、error（发送错误调用）、route（路由之时）和static
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //过滤器的执行顺序，当过滤器很多时，这个方法会有意义
        return 0;
    }

    @Override
    public boolean shouldFilter() {
//        return false;
        //在实际开发中，我们可以根据当前请求地址来决定要不要对该地址进行过滤，这里直接返回true。
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //假设请求地址中携带了login参数的话，则认为是合法请求，否则就是非法请求，
        //如果是非法请求的话，首先设置ctx.setSendZuulResponse(false);表示不对该请求进行路由，然后设置响应码和响应值。
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String login = request.getParameter("login");
        if (login == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.addZuulResponseHeader("content-type","text/html;charset=utf-8");
            ctx.setResponseBody("非法访问");
        }

        return null;
    }
}
