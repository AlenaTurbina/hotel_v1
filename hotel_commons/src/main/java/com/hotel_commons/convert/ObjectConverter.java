package com.hotel_commons.convert;

import java.math.BigInteger;

public class ObjectConverter {

    public static Integer convertDataObjectBigIntIntoInteger(Object data) {
        BigInteger dataConvert = (BigInteger) data;
        return dataConvert.intValue();
    }
}
