package com.ucc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://10.10.1.83:8888");
//        configuration.addAllowedOrigin("http://10.10.1.83:8888");
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedOrigin("*");
////        configuration.addAllowedOrigin("http://10.10.1.204:8888");
////        configuration.addAllowedOrigin("http://localhost:8889");
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");

        //允许所有域名进行跨域调用
        configuration.addAllowedOriginPattern("*");//替换这个
//        configuration.addAllowedOriginPattern("http://10.10.1.83:8888");//替换这个

        //允许跨越发送cookie
        configuration.setAllowCredentials(true);
        //放行全部原始头信息
        configuration.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }
}
