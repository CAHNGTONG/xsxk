<template>
  <!-- 模板部分代码 -->
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-select v-model="data.specialityId" placeholder="请选择专业">
        <el-option
            v-for="item in data.specialityData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
        ></el-option>
      </el-select>
      <el-input v-model="data.grade" style="width: 300px; margin-right: 10px" placeholder="请输入年级"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
    </div>

    <div class="card" v-if="data.trainingProgram">
      <el-tree :data="data.treeData" :props="defaultProps" node-key="id">
        <template #default="{ node, data }">
          <span v-if="data.type === 'category'">{{ data.name }}</span>
          <span v-else>
            {{ data.name }} (学分: {{ data.score }}, 开课学期: {{ data.semester }}, 课程性质: {{ data.nature }})
          </span>
        </template>
      </el-tree>

      <div v-for="category in data.categoryData" :key="category.id">
        <p>{{ category.name }} 完成进度：</p>
        <el-progress :percentage="category.progress" :status="category.status"></el-progress>
      </div>

      <div v-if="data.unfinishedRequiredCourses.length > 0">
        <p style="color: red">未通过的必修课清单：</p>
        <ul>
          <li v-for="course in data.unfinishedRequiredCourses" :key="course.id">{{ course.name }}</li>
        </ul>
      </div>
      <p v-if="data.totalCreditNotEnough" style="color: red">总学分未达标！</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '@/utils/request';

const data = ref({
  specialityId: null,
  grade: null,
  specialityData: [],
  trainingProgram: null,
  treeData: [],
  categoryData: [],
  unfinishedRequiredCourses: [],
  totalCreditNotEnough: false
});

const defaultProps = {
  children: 'children',
  label: 'name'
};

const loadSpeciality = () => {
  request.get('/speciality/selectAll').then(res => {
    if (res.code === '200') {
      data.value.specialityData = res.data;
    } else {
      ElMessage.error(res.msg);
    }
  });
};

const load = () => {
  if (!data.value.specialityId || !data.value.grade) {
    ElMessage.warning('请选择专业和输入年级');
    return;
  }

  request.get('/trainingProgram/selectBySpecialityAndGrade', {
    params: {
      specialityId: data.value.specialityId,
      grade: data.value.grade
    }
  }).then(res => {
    if (res.code === '200' && res.data.length > 0) {
      data.value.trainingProgram = res.data[0];
      loadCourseCategories(data.value.trainingProgram.id);
    } else {
      ElMessage.error('未找到对应的培养方案');
    }
  });
};

const loadCourseCategories = (trainingProgramId) => {
  request.get('/courseCategory/selectByTrainingProgramId', {
    params: {
      trainingProgramId
    }
  }).then(res => {
    if (res.code === '200') {
      data.value.categoryData = res.data;
      const treeData = [];
      data.value.categoryData.forEach(category => {
        const categoryNode = {
          id: category.id,
          name: category.name,
          type: 'category',
          children: []
        };
        loadCoursesByCategory(category.id, categoryNode.children);
        treeData.push(categoryNode);
      });
      data.value.treeData = treeData;
      calculateCreditProgress();
    } else {
      ElMessage.error('未找到课程分类信息');
    }
  });
};

const loadCoursesByCategory = (categoryId, children) => {
  request.get('/course/selectByCategoryId', {
    params: {
      categoryId
    }
  }).then(res => {
    if (res.code === '200') {
      children.push(...res.data);
    } else {
      ElMessage.error('未找到课程信息');
    }
  });
};

const calculateCreditProgress = () => {
  // 计算学分进度和毕业要求检查的逻辑
  // 这里需要根据实际情况查询学生选课记录和课程信息进行计算
  // 示例代码，假设已经有学生选课记录和课程信息
  const studentChoices = []; // 学生选课记录
  const totalRequiredCredit = 100; // 总毕业要求学分
  let totalEarnedCredit = 0;
  let unfinishedRequiredCourses = [];

  data.value.categoryData.forEach(category => {
    let categoryRequiredCredit = 0;
    let categoryEarnedCredit = 0;
    category.children.forEach(course => {
      const isPassed = studentChoices.some(choice => choice.courseId === course.id);
      if (course.nature === '必修') {
        categoryRequiredCredit += course.score;
        if (!isPassed) {
          unfinishedRequiredCourses.push(course);
        }
      }
      if (isPassed) {
        categoryEarnedCredit += course.score;
        totalEarnedCredit += course.score;
      }
    });
    // 计算每个分类的进度
    category.progress = categoryRequiredCredit > 0 ? Math.round((categoryEarnedCredit / categoryRequiredCredit) * 100) : 0;
    category.status = category.progress === 100 ? 'success' : 'warning';
  });

  data.value.unfinishedRequiredCourses = unfinishedRequiredCourses;
  data.value.totalCreditNotEnough = totalEarnedCredit < totalRequiredCredit;
};
</script>

loadSpeciality();
loadCourseCategories();
