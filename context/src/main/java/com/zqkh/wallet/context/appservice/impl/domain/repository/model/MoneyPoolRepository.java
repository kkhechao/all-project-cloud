package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.MoneyPool;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.MoneyPoolDmoMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.MoneyPoolDmo;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:30
 */
@Repository("MoneyPool_Repository")
public class MoneyPoolRepository implements IRepository<MoneyPool> {

    @Autowired
    private MoneyPoolDmoMapper moneyPoolDmoMapper;

    @Autowired
    private DozerBeanMapper modelMapper;

    @Override
    public MoneyPool getEntityById(Identifier identifier, EntityLoader<MoneyPool> entityLoader) {
        MoneyPoolDmo moneyPoolDmo = moneyPoolDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (moneyPoolDmo == null) {
            return null;
        }
        MoneyPool moneyPool = entityLoader.create(identifier);
        modelMapper.map(moneyPoolDmo, moneyPool);

        return moneyPool;
    }

    @Override
    public void save(MoneyPool moneyPool) {
        if (moneyPool == null) {
            return;
        }

        MoneyPoolDmo moneyPoolDmo = modelMapper.map(moneyPool, MoneyPoolDmo.class);

        if(moneyPoolDmoMapper.updateByPrimaryKey(moneyPoolDmo) == 0){
            moneyPoolDmoMapper.insert(moneyPoolDmo);
        }
    }

    @Override
    public void remove(MoneyPool moneyPool) {

    }
}
