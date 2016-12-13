package com.epam.rd.backend.core.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Olga_Kramska on 9/19/2016.
 */
public abstract class AbstractConverter<S, T> {
    public abstract T convert(S sourceType);

    public List<T> convertList(List<S> sourceList) {
        return sourceList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
