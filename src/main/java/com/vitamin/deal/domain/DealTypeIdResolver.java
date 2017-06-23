package com.vitamin.deal.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DealTypeIdResolver implements TypeIdResolver {

    private static final String DEAL_PACKAGE = Deal.class.getPackage().getName();
    private JavaType mBaseType;

    @Override
    public void init(JavaType baseType) {
        mBaseType = baseType;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

    @Override
    public String idFromValue(Object obj) {
        return idFromValueAndType(obj, obj.getClass());
    }

    @Override
    public String idFromBaseType() {
        return idFromValueAndType(null, mBaseType.getRawClass());
    }

    @Override
    public String idFromValueAndType(Object obj, Class<?> clazz) {
        if(obj instanceof Deal){
            return ((Deal)obj).getType();
        }

        throw new IllegalArgumentException("Unknown type:" + obj.toString());
    }

    @Override
    public String getDescForKnownTypeIds() {
        return null;
    }

    @Override
    public JavaType typeFromId(DatabindContext databindContext, String type) {
        Class<?> clazz = DealTypes.valueOf(type.toUpperCase()).getClazz();
        return TypeFactory.defaultInstance().constructSpecializedType(mBaseType, clazz);
    }
}
