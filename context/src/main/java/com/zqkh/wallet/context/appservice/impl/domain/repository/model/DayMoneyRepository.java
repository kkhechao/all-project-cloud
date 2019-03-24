package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.DayMoney;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.DayMoneyDmoMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.DayMoneyDmo;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wenjie
 * @date 2018/1/2 0002 19:00
 */
@Repository("DayMoney_Repository")
public class DayMoneyRepository implements IRepository<DayMoney> {

    @Autowired
    private DayMoneyDmoMapper dayMoneyDmoMapper;

    @Autowired
    private DozerBeanMapper modelMapper;


    @Override
    public DayMoney getEntityById(Identifier identifier, EntityLoader<DayMoney> entityLoader) {
        DayMoneyDmo dayMoneyDmo = dayMoneyDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (dayMoneyDmo == null) {
            return null;
        }
        DayMoney dayMoney = entityLoader.create(identifier);
        modelMapper.map(dayMoneyDmo, dayMoney);

        return dayMoney;
    }

    @Override
    public void save(DayMoney dayMoney) {
        if (dayMoney == null) {
            return;
        }

        DayMoneyDmo dayMoneyDmo = modelMapper.map(dayMoney, DayMoneyDmo.class);

        if(dayMoneyDmoMapper.updateByPrimaryKey(dayMoneyDmo) == 0){
            dayMoneyDmoMapper.insert(dayMoneyDmo);
        }
    }

    @Override
    public void remove(DayMoney dayMoney) {

    }
}
