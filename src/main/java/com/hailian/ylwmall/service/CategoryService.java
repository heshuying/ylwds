package com.hailian.ylwmall.service;

import com.hailian.ylwmall.controller.vo.NewBeeMallIndexCategoryVO;
import com.hailian.ylwmall.controller.vo.SearchPageCategoryVO;
import com.hailian.ylwmall.entity.GoodsCategory;
import com.hailian.ylwmall.util.PageQueryUtil;
import com.hailian.ylwmall.util.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<NewBeeMallIndexCategoryVO> getCategoriesForIndex();

    /**
     * 返回分类数据(搜索页调用)
     *
     * @param categoryId
     * @return
     */
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);

    List<Long> getThirdLevelByFirstLevelId(Long categoryId);
}
