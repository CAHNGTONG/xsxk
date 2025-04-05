package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

// 移除未使用且无法解析的 import 语句
// import javax.annotation.Resource;

import java.util.List;

/**
 * 课程信息业务层处理
 */
@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    public void add(Course course) {
        course.setAlreadyNum(0);
        courseMapper.insert(course);
    }

    //新增
    public List<Course> selectByCategoryId(Integer categoryId) {
        return courseMapper.selectByCategoryId(categoryId);
    }
    //原有





    public PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize) {
        List<Course> list;
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtil.isNotEmpty(course.getTeacherId())) {
            Integer teacherId = course.getTeacherId();
            // 表示当前登录的是教师这个角色
            if (ObjectUtil.isNotEmpty(course.getName())) {
                list = courseMapper.selectByNameAndTeacherId(course.getName(), teacherId);
            } else {
                list = courseMapper.selectAllByTeacherId(teacherId);
            }
        } else {
            if (ObjectUtil.isNotEmpty(course.getName())) {
                list = courseMapper.selectByName(course.getName());
            } else {
                list = courseMapper.selectAll();
            }
        }
        return PageInfo.of(list);
    }

    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }
}
