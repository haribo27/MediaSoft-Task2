package ru.zubcov.mediasoft.task2.mediasofttask2.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.TeamDto;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDto mapTeamDto(Team team);

    Team mapToTeam(NewTeamRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeam(UpdateTeamRequest request, @MappingTarget Team team);
}
