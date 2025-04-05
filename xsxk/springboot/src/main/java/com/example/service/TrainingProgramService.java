package com.example.service;

import com.example.entity.TrainingProgram;
import com.example.mapper.TrainingProgramMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingProgramService {
    @Resource
    private TrainingProgramMapper trainingProgramMapper;

    public List<TrainingProgram> selectBySpecialityId(Integer specialityId) {
        return trainingProgramMapper.selectBySpecialityId(specialityId);
    }
}