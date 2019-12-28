package com.he.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListDto<T> implements Dto {

    private List<T> list;

    public ListDto(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ListDto{" +
                "list=" + list +
                '}';
    }

    public static <T, R> ListDto<R> fromList(List<T> from, Function<T, R> mapper) {
        return new ListDto<>(from.stream().map(mapper).collect(Collectors.toList()));
    }

}
