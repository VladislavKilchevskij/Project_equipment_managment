package com.example.stuffmanagerapp.mapper.request;

import com.example.stuffmanagerapp.dto.RequestDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Request;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { RequestMapper.class })
public interface RequestListMapper extends DefaultListMapper<RequestDto, Request> {
}
