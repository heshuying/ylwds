package ltd.newbee.mall;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@MapperScan("ltd.newbee.mall.dao")
@EnableDubboConfiguration
@SpringBootApplication
public class B2BMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(B2BMallApplication.class, args);
    }
}
