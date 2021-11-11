package pers.brian.mall.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.brian.mall.modules.ums.model.UmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @Author: BrianHu
 * @Date: 2021-11-11 11:11
 * @Version: 0.0.1
 **/
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据后台用户ID获取菜单
     */
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);
    /**
     * 根据角色ID获取菜单
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

}
