package com.lojavirtual.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDouble implements Converter<String,Double> {

    @Override
    public Double convert(String source) {
        source = source.trim();
        if(source.length()>0){
            source = source.replace(".","").replace(",",".");
            return Double.parseDouble(source);
        }
        return 0.;
    }

//    @Override
//    public <U> Converter<String, U> andThen(Converter<? super Double, ? extends U> after) {
//        return Converter.super.andThen(after);
//    }
}
