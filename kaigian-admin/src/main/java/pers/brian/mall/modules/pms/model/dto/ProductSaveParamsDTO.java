package pers.brian.mall.modules.pms.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.brian.mall.modules.pms.model.entity.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Author: BrianHu
 * @Create: 2021-11-29 15:19
 * @Version: 0.0.1
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductConditionDTO商品保存数据传输对象", description = "用于商品的添加、修改保存的参数接收")
public class ProductSaveParamsDTO extends PmsProduct {

    private static final long serialVersionUID = 2887475979938437896L;

    /**
     * 会员价格
     */
    @ApiModelProperty(value = "会员价格")
    private List<PmsMemberPrice> memberPriceList;

    /**
     * 商品满减
     */
    @ApiModelProperty(value = "商品满减")
    private List<PmsProductFullReduction> productFullReductionList;

    /**
     * 商品阶梯价格
     */
    @ApiModelProperty(value = "商品阶梯价格")
    private List<PmsProductLadder> productLadderList;

    /**
     * 商品属性相关
     */
    @ApiModelProperty(value = "商品属性相关")
    private List<PmsProductAttributeValue> productAttributeValueList;

    /**
     * 商品sku库存信息，嵌套验证支持
     */
    @ApiModelProperty(value = "商品sku库存信息")
    @Size(min = 1, message = "SKU至少要有一项")
    @Valid
    private List<PmsSkuStock> skuStockList;
}
