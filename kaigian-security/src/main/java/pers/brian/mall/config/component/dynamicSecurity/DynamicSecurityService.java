package pers.brian.mall.config.component.dynamicSecurity;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Map;

/**
 * 动态权限相关业务类
 *
 * @author BrianHu
 * @create 2021-12-28 10:58
 **/
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     * key: 匹配器 （在DynamicSecurityMetadataSource起作用）
     * value: 资源所对应的角色
     *
     * @return
     */
    Map<RequestMatcher, List<ConfigAttribute>> loadDataSource();
}
