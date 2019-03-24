package com.zqkh.wallet.context.appservice.impl.domain.repository.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.ddd.repository.IRepository;
import com.zqkh.wallet.context.appservice.impl.domain.SerialLog;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.SerialLogDmoMapper;
import com.zqkh.wallet.context.appservice.impl.domain.repository.mappers.dmo.SerialLogDmo;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wenjie
 * @date 2017/11/27 0027 15:15
 */
@Repository("SerialLog_Repository")
public class SerialLogRepository implements IRepository<SerialLog> {

    @Autowired
    private DozerBeanMapper modelMapper;
    @Autowired
    private SerialLogDmoMapper serialLogDmoMapper;

    @Autowired
    DozerBeanMapper dozerBeanMapper;

    @Override
    public SerialLog getEntityById(Identifier identifier, EntityLoader<SerialLog> entityLoader) {
        SerialLogDmo serialLogDmo = serialLogDmoMapper.selectByPrimaryKey(identifier.toValue());
        if (serialLogDmo == null) {
            return null;
        }
        SerialLog serialLog = entityLoader.create(identifier);
        dozerBeanMapper.map(serialLogDmo, serialLog);
        return serialLog;
    }

    @Override
    public void save(SerialLog serialLog) {
        if (serialLog == null) {
            return;
        }

        SerialLogDmo serialLogDmo = modelMapper.map(serialLog, SerialLogDmo.class);

        if (serialLogDmoMapper.updateByPrimaryKey(serialLogDmo) == 0) {
            serialLogDmoMapper.insert(serialLogDmo);
        }

    }

    @Override
    public void remove(SerialLog serialLog) {

    }
}
