package pers.brian.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author: BrianHu
 * @Create: 2021-12-01 11:58
 * @Version: 0.0.1
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="首页推荐类型及商品列表数据传输对象", description="首页推荐类型及商品列表数据传输对象")
public class HomeGoodsSaleDTO {
    private String categoryName;

    private String pic;

    @ApiModelProperty(value = "链接地址")
    private String url;

    private List<ProductDTO> productList;

}
