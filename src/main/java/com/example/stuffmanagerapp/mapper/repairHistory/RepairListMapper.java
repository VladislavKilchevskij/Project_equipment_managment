package com.example.stuffmanagerapp.mapper.repairHistory;

import com.example.stuffmanagerapp.dto.RepairHistoryDto;
import com.example.stuffmanagerapp.mapper.defaultMappers.DefaultListMapper;
import com.example.stuffmanagerapp.model.entity.RepairHistory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = { RepairMapper.class })
public interface RepairListMapper extends DefaultListMapper<RepairHistoryDto, RepairHistory> {
}
