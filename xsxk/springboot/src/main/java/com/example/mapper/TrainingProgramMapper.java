package com.example.mapper;

import com.example.entity.TrainingProgram;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainingProgramMapper {
    List<TrainingProgram> selectBySpecialityId(Integer specialityId);
}