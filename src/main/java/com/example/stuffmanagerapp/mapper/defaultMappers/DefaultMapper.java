package com.example.stuffmanagerapp.mapper.defaultMappers;

public interface DefaultMapper<T, E> {
    T toDto(E entity);
    E toEntity(T dto);
}
