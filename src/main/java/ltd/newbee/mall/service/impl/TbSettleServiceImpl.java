package ltd.newbee.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.dto.SettleCreateDto;
import ltd.newbee.mall.dto.SettleQueryDto;
import ltd.newbee.mall.dto.SettleUpdateDto;
import ltd.newbee.mall.dto.UnSettleListDto;
import ltd.newbee.mall.entity.TbSettle;
import ltd.newbee.mall.dao.TbSettleDao;
import ltd.newbee.mall.entity.TbSettleDetail;
import ltd.newbee.mall.service.TbSettleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.newbee.mall.util.Const;
import ltd.newbee.mall.util.DateTimeUtil;
import ltd.newbee.mall.util.Query;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 结算表 服务实现类
 * </p>
 *
 * @author 19012964
 * @since 2020-04-20
 */
@Service
public class TbSettleServiceImpl extends ServiceImpl<TbSettleDao, TbSettle> implements TbSettleService {
    @Override
    public Result queryUnSettle(SettleQueryDto queryDto) {
        List<UnSettleListDto> list=baseMapper.querySettleList(queryDto.getSupplierId(),
                queryDto.getStartDate(), queryDto.getEndDate());


        Result result= ResultGenerator.genSuccessResult();
        result.setData(list);
        return result;
    }

    @Override
    public Result querySettle(SettleQueryDto queryDto) {
        QueryWrapper<TbSettle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(queryDto.getSupplierId()!=null,"supplier_id", queryDto.getSupplierId());
        queryWrapper.eq(queryDto.getStatus()!=null,"status", queryDto.getStatus());
        queryWrapper.orderByDesc("id");

        Map<String, Object> params =new HashedMap();
        params.put(Constants.PAGE,queryDto.getPage().toString());
        params.put(Constants.LIMIT,queryDto.getLimit().toString());
        IPage<TbSettle> page = baseMapper.selectPage(
                new Query<TbSettle>().getPage(params),
                queryWrapper
        );
        Result result= ResultGenerator.genSuccessResult();
        result.setData(page);
        return result;
    }

    @Override
    public Result updateSettle(SettleUpdateDto dto) {
        TbSettle tbSettle=new TbSettle();
        tbSettle.setId(dto.getSettleId());
        tbSettle.setStatus(dto.getStatus());
        if(StringUtils.isNoneBlank(dto.getBillImg())){

        }
        Integer affected= baseMapper.updateById(tbSettle);
        return ResultGenerator.genSuccessResult();
    }

    @Transactional
    @Override
    public Result createSettle(SettleCreateDto dto) {
        //创建结算单
        TbSettle tbSettle =new TbSettle();
        tbSettle.setSettleNo("");
        tbSettle.setSettleName("");
        tbSettle.setStatus(Const.SettleStatus.Uncheck.getKey());
        tbSettle.setSupplierId(dto.getSupplierId());
        tbSettle.setAmount(BigDecimal.ONE);
        tbSettle.setBeginPeriod(DateTimeUtil.format( dto.getStartDate()));
        tbSettle.setEndPeriod(DateTimeUtil.format( dto.getEndDate()));
        tbSettle.setCreateTime(new Date());
        baseMapper.insert(tbSettle);

        //结算明细
        List<TbSettleDetail> details=new ArrayList<>();
        for (UnSettleListDto unSettle:dto.getUnSettleList()
             ) {
            TbSettleDetail detail=new TbSettleDetail();
            detail.setCreateTime(new Date());
            detail.setSettleId(tbSettle.getId());
            detail.setOrderId(unSettle.getOrderId());
            detail.setSettleNum(unSettle.getSaleNum());
            detail.setSettlePrice(unSettle.getBuyingPrice());
            details.add(detail);
        }
        //更新订单到结算中

        return null;
    }
}
