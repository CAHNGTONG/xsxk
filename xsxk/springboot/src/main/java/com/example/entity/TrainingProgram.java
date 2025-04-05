package com.example.entity;

public class TrainingProgram {
    private Integer id;
    private Integer specialityId;
    private Integer courseId;
    private Integer categoryId;
    private Boolean isCompulsory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIsCompulsory() {
        return isCompulsory;
    }

    public void setIsCompulsory(Boolean isCompulsory) {
        this.isCompulsory = isCompulsory;
    }
}