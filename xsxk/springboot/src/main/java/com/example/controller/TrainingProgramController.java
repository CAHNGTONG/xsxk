package com.example.controller;

import com.example.common.Result;
import com.example.entity.CourseCategory;
import com.example.entity.TrainingProgram;
import com.example.service.CourseCategoryService;
import com.example.service.TrainingProgramService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-program")
public class TrainingProgramController {
    @Resource
    private TrainingProgramService trainingProgramService;
    @Resource
    private CourseCategoryService courseCategoryService;

    @GetMapping("/categories")
    public Result getCourseCategories() {
        List<CourseCategory> categories = courseCategoryService.selectAll();
        return Result.success(categories);
    }

    @GetMapping("/{specialityId}")
    public Result getTrainingProgramBySpecialityId(@PathVariable Integer specialityId) {
        List<TrainingProgram> programs = trainingProgramService.selectBySpecialityId(specialityId);
        return Result.success(programs);
    }
}