package pers.brian.mall.modules.pms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import pers.brian.mall.common.api.CommonResult;
import pers.brian.mall.modules.pms.dto.RelationAttrInfoDTO;
import pers.brian.mall.modules.pms.service.PmsProductAttributeService;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author BrianHu
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    private final PmsProductAttributeService productAttributeService;

    @Autowired
    public PmsProductAttributeController(PmsProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }


    /**
     * 根据商品分类id获取关联的筛选属性
     *
     * @param cId 商品分类id
     * @return 关联的筛选属性
     */
    @RequestMapping(value = "/attrInfo/{cId}")
    public CommonResult<List<RelationAttrInfoDTO>> getRelationAttrInfoByCid(@PathVariable Long cId) {
        List<RelationAttrInfoDTO> list = productAttributeService.getRelationAttrInfoByCid(cId);
        return CommonResult.success(list);
    }
}

