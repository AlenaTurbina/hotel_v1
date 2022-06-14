package com.hotel_commons.error;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Iterator;
import java.util.Map;

public class Errors {

    public static BindingResult getBindingResultFromMapErrors(Map<String, String> mapErrors, BindingResult bindingResult, String objectName) {
        Iterator<Map.Entry<String, String>> entries = mapErrors.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            FieldError error = new FieldError(objectName, entry.getKey(), entry.getValue());
            bindingResult.addError(error);
        }
        return bindingResult;
    }
}
