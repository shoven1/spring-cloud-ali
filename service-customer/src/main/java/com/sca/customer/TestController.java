package com.sca.customer;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author xiaojing
 */
@RestController
public class TestController {

    @Autowired
    private CustomerApplication.EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/test")
    public String test() {
        return "4564dfdsff233";
    }

    @RequestMapping(value = "/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @SentinelResource("GET:http://service-provider/echo/{str}")
    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String rest(@PathVariable String str) {
//        return restTemplate.getForObject("http://service-provider/echo/" + str,
//                String.class);
         return echoService.echo(str);
    }

    @RequestMapping(value = "/echo-hello1/{str}", method = RequestMethod.GET)
    public String hello1(@PathVariable String str) {
//        return restTemplate.getForObject("http://service-provider/echo/" + str,
//                String.class);
        return echoService.hello(str);
    }

    @RequestMapping(value = "/echo-hello2/{str}", method = RequestMethod.GET)
    public String hello2(@PathVariable String str) {
//        return restTemplate.getForObject("http://service-provider/echo/" + str,
//                String.class);
        return "echo hello2 no more service ";
    }



    @RequestMapping(value = "/notFound-feign", method = RequestMethod.GET)
    public String notFound() {
        return echoService.notFound();
    }

    @RequestMapping(value = "/divide-feign", method = RequestMethod.GET)
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        return echoService.divide(a, b);
    }

    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }

    @RequestMapping(value = "/services/{service}", method = RequestMethod.GET)
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public Object services() {
        return discoveryClient.getServices();
    }
}
