package com.example.mapper;

import com.example.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseCategoryMapper {
    List<CourseCategory> selectAll();
}
