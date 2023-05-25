package cn.itcast.order.web;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import cn.itcast.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Resource
    private OrderMapper orderMapper;
    //2.
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        //传入userID
        Order order=orderMapper.findById(orderId);
        //3.基于order的user ID发起http请求,利用restTemplate.getForObject发送get请求,并返回json数据回来
        //硬编码url地址
       String url ="http://localhost:8081/user/"+order.getUserId();

        User user = restTemplate.getForObject(url, User.class);//User.class表示将返回的json数据封装成User类型
        // 根据id查询订单并返回
        order.setUser(user);
        return orderService.queryOrderById(orderId);
    }
}
