package com.example.stuffmanagerapp.mapper.defaultMappers;


import java.util.List;

public interface DefaultListMapper<T, E> {

    List<T> toDtoList(List<E> entities);
    List<E> toEntityList(List<T> dtos);

}
