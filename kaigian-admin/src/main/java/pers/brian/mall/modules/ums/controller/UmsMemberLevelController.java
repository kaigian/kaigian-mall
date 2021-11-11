package pers.brian.mall.modules.ums.controller;


import pers.brian.mall.common.api.CommonResult;
import pers.brian.mall.modules.ums.model.UmsMemberLevel;
import pers.brian.mall.modules.ums.service.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:  * <p>
 *  * 会员等级表 前端控制器
 *  * </p>
 * @Author: BrianHu
 * @Date: 2021-11-11 11:11
 * @Version: 0.0.1
 **/
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Autowired
	UmsMemberLevelService memberLevelService;

    /**
     *   url:'/memberLevel/list',
     *     method:'get',
     *     params:{defaultStatus: 0}
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public CommonResult list(
            @RequestParam(value="defaultStatus",defaultValue = "0") Integer defaultStatus)
    {
        List<UmsMemberLevel> list= memberLevelService.list(defaultStatus);
        return CommonResult.success(list);
    }
}
