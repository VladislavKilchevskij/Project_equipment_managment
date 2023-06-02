package com.example.stuffmanagerapp.mapper.acceptance;

import com.example.stuffmanagerapp.dto.AcceptanceDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Acceptance;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { AcceptanceMapper.class })
public interface AcceptanceListMapper extends DefaultListMapper<AcceptanceDto, Acceptance> {
}
