package com.example.service;

import com.example.entity.CourseCategory;
import com.example.mapper.CourseCategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    public List<CourseCategory> selectAll() {
        return courseCategoryMapper.selectAll();
    }
}