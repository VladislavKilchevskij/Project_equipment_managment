package com.example.stuffmanagerapp.mapper.writeoff;

import com.example.stuffmanagerapp.dto.writeoff.WriteoffDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.Writeoff;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { WriteoffMapper.class })
public interface WriteoffListMapper extends DefaultListMapper<WriteoffDto, Writeoff> {
}
