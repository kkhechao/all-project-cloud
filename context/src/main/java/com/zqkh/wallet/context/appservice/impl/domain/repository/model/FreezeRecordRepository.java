package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.FreezeRecord;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.FreezeRecordDmoMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.FreezeRecordDmo;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wenjie
 * @date 2017/11/27 0027 16:40
 */
@Repository("FreezeRecord_Repository")
public class FreezeRecordRepository implements IRepository<FreezeRecord>{

    @Autowired
    private DozerBeanMapper modelMapper;

    @Autowired
    private FreezeRecordDmoMapper freezeRecordDmoMapper;

    @Override
    public FreezeRecord getEntityById(Identifier identifier, EntityLoader<FreezeRecord> entityLoader) {
        return null;
    }

    @Override
    public void save(FreezeRecord freezeRecord) {
        if (freezeRecord == null) {
            return;
        }

        FreezeRecordDmo freezeRecordDmo = modelMapper.map(freezeRecord, FreezeRecordDmo.class);

        if(freezeRecordDmoMapper.updateByPrimaryKey(freezeRecordDmo) == 0){
            freezeRecordDmoMapper.insert(freezeRecordDmo);
        }
    }

    @Override
    public void remove(FreezeRecord freezeRecord) {

    }
}
