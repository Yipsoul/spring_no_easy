package cn.it.yip.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-19 15:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeBeanReference {
    private String beanName;
}
