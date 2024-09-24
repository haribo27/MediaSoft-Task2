package ru.zubcov.mediasoft.task2.mediasofttask2.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.MatchResultDto;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;

@Mapper(componentModel = "spring")
public interface MatchResultMapper {

    MatchResult mapMatchResult(NewMatchResultRequest request);

    MatchResultDto mapToMatchResultDto(MatchResult matchResult);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMatchResult(UpdateMatchResultRequest request, @MappingTarget MatchResult matchResult);

}
