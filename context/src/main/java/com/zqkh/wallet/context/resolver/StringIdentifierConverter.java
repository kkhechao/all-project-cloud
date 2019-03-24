package com.zqkh.wallet.context.resolver;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import org.dozer.DozerConverter;

/**
 * Created by zhaofujun on 2017/8/12.
 */
public class StringIdentifierConverter extends DozerConverter<Identifier, String> {


    public StringIdentifierConverter() {
        super(Identifier.class, String.class);
    }

    @Override
    public String convertTo(Identifier source, String destination) {
        return source.toValue();
    }

    @Override
    public Identifier convertFrom(String source, Identifier destination) {
        return new StringIdentifier(source);
    }
}
