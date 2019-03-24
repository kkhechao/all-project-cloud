package com.zqkh.wallet.context.appservice.impl.domain.identifier;

import com.jovezhao.nest.ddd.Identifier;
import lombok.NoArgsConstructor;

/**
 * @author wenjie
 * @date 2018/1/2 0002 18:32
 */
@NoArgsConstructor
public class SourceIdentifier extends Identifier{

    private String source;

    public SourceIdentifier(String source){
        this.source = source;
    }

    public static SourceIdentifier valueOf(String source) {
        return new SourceIdentifier(source);
    }

    public String toString() {
        return this.toValue();
    }

    @Override
    public String toValue() {
        return this.source;
    }
}
