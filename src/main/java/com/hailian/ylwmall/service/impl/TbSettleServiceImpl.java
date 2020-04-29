package com.hailian.ylwmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.dto.SettleQueryDto;
import com.hailian.ylwmall.dto.SettleUpdateDto;
import com.hailian.ylwmall.dto.UnSettleListDto;
import com.hailian.ylwmall.entity.TbSettle;
import com.hailian.ylwmall.entity.TbSettleDetail;
import com.hailian.ylwmall.service.TbSettleService;
import com.hailian.ylwmall.util.Const;
import com.hailian.ylwmall.util.DateTimeUtil;
import com.hailian.ylwmall.util.Query;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import com.hailian.ylwmall.util.SystemUtil;
import com.hailian.ylwmall.dto.SettleCreateDto;
import com.hailian.ylwmall.dao.TbSettleDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String no= SystemUtil.buildSettleNo(dto.getSupplierId());
        tbSettle.setSettleNo(no);
        String name=SystemUtil.buildSettleName(dto.getSupplierName(),dto.getStartDate(), dto.getEndDate());
        tbSettle.setSettleName(name);
        tbSettle.setStatus(Const.SettleStatus.Uncheck.getKey());
        tbSettle.setSupplierId(dto.getSupplierId());
        Double sumAmount= dto.getUnSettleList().stream().mapToDouble(
                m->Double.parseDouble(m.getBuyingPrice().toString())
        ).sum();
        tbSettle.setAmount(BigDecimal.valueOf(sumAmount));
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
            detail.setSettlePrice(unSettle.getSellingPrice());
            details.add(detail);
        }
        //更新订单到结算中
        List<Long> orderIds=details.stream().map(m->m.getOrderId()).collect(Collectors.toList());
        baseMapper.updateBatchOrderStatus(7,orderIds);
        return ResultGenerator.genSuccessResult();
    }
}
