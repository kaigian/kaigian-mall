package pers.brian.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.brian.mall.modules.pms.model.dto.PmsProductCategoryChildrenDTO;
import pers.brian.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import pers.brian.mall.modules.pms.mapper.PmsProductCategoryMapper;
import pers.brian.mall.modules.pms.model.po.PmsProductCategory;
import pers.brian.mall.modules.pms.model.po.PmsProductCategoryAttributeRelation;
import pers.brian.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import pers.brian.mall.modules.pms.service.PmsProductCategoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author BrianHu
 * @since 2021-11-15
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    private final PmsProductCategoryMapper productCategoryMapper;

    private final PmsProductCategoryAttributeRelationService productCategoryAttributeRelationService;

    @Autowired
    public PmsProductCategoryServiceImpl(PmsProductCategoryMapper productCategoryMapper,
                                         PmsProductCategoryAttributeRelationService productCategoryAttributeRelationService) {
        this.productCategoryMapper = productCategoryMapper;
        this.productCategoryAttributeRelationService = productCategoryAttributeRelationService;
    }

    @Override
    public Page<PmsProductCategory> page(Long parentId, Integer pageNum, Integer pageSize) {
        Page<PmsProductCategory> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PmsProductCategory> productCategoryQueryWrapper = new QueryWrapper<>();
        productCategoryQueryWrapper.lambda().eq(PmsProductCategory::getParentId, parentId);
        return this.page(page, productCategoryQueryWrapper);
    }

    @Override
    public boolean updateNavStatus(Integer navStatus, List<Long> ids) {
        UpdateWrapper<PmsProductCategory> productCategoryUpdateWrapper = new UpdateWrapper<>();
        productCategoryUpdateWrapper.lambda()
                .set(PmsProductCategory::getNavStatus, navStatus)
                .in(PmsProductCategory::getId, ids);
        return this.update(productCategoryUpdateWrapper);
    }

    @Override
    public boolean updateShowStatus(Integer showStatus, List<Long> ids) {
        UpdateWrapper<PmsProductCategory> productCategoryUpdateWrapper = new UpdateWrapper<>();
        productCategoryUpdateWrapper.lambda()
                .set(PmsProductCategory::getShowStatus, showStatus)
                .in(PmsProductCategory::getId, ids);
        return this.update(productCategoryUpdateWrapper);
    }

    @Override
    public List<PmsProductCategoryChildrenDTO> listWithChildren() {
        return productCategoryMapper.listWithChildren();
    }

    @Override
    public boolean customSave(PmsProductCategoryDTO productCategoryDTO) {
        // 保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        // 通过BeanUtils 将productCategoryDTO的数据拷贝到productCategory
        // 为什么要拷贝：因为一定要通过this.save 去保存PmsProductCategory，因为只有它才映射了@TableName
        BeanUtils.copyProperties(productCategoryDTO, productCategory);
        // 由于商品数量 和级别 在表单中没有维护， 需要设置默认值
        productCategory.setProductCount(0);
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            // 如果有多级分类，根据parentId查出商品分类获取level+1
            // 由于只有2级分类，直接设置为1
            productCategory.setLevel(1);
        }
        this.save(productCategory);
        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }

    @Override
    public boolean update(PmsProductCategoryDTO productCategoryDTO) {
        // 保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        // 通过BeanUtils 将productCategoryDTO的数据拷贝到productCategory
        // 为什么要拷贝：因为一定要通过this.save 去保存PmsProductCategory，因为只有它才映射了@TableName
        BeanUtils.copyProperties(productCategoryDTO, productCategory);
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            // 如果有多级分类，根据parentId查出商品分类获取level+1
            // 由于只有2级分类，直接设置为1
            productCategory.setLevel(1);
        }
        this.updateById(productCategory);

        // 删除已保存的关联属性—根据商品分类id删除
        QueryWrapper<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationQueryWrapper = new QueryWrapper<>();
        productCategoryAttributeRelationQueryWrapper.lambda()
                .eq(PmsProductCategoryAttributeRelation::getProductCategoryId, productCategory.getId());
        productCategoryAttributeRelationService.remove(productCategoryAttributeRelationQueryWrapper);
        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }

    /**
     * 添加关联属性
     *
     * @param productCategoryDTO
     * @param productCategory
     * @return
     */
    private boolean saveAttrRelation(PmsProductCategoryDTO productCategoryDTO, PmsProductCategory productCategory) {
        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
        for (Long attrId : productAttributeIdList) {
            // 得到分类保存后的主键id,   保存商品分类筛选属性关系
            PmsProductCategoryAttributeRelation productCategoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            productCategoryAttributeRelation.setProductCategoryId(productCategory.getId());
            productCategoryAttributeRelation.setProductAttributeId(attrId);
            list.add(productCategoryAttributeRelation);
        }
        return productCategoryAttributeRelationService.saveBatch(list);
    }
}
