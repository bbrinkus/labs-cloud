package com.brinkus.labs.cloud.service.component;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface Converter<I, O> {

    O convert(I inData);

    default List<O> convert(final Iterable<I> inList) {
        List<O> outList = new ArrayList<>();
        inList.forEach(inData -> outList.add(convert(inData)));
        return outList;
    }

}