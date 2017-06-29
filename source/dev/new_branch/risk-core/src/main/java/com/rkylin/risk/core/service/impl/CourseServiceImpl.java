package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.CourseDao;
import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.service.CourseService;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;

    @Override
    public List<Course> queryByUserrelatedid(String userrelatedid) {
        return courseDao.queryByUserrelatedid(userrelatedid);
    }

    @Override public List<String> queryCourseNameByUserrelatedid(String userrelatedid) {
        return courseDao.queryCourseNameByUserrelatedid(userrelatedid);
    }

    @Override public Course queryByMerchantidAndCourseName(String merchantid, String coursename) {
        return courseDao.queryByMerchantidAndCourseName(merchantid, coursename);
    }

    @Override public void addCourseBatch(List<Course> courses) {
        courseDao.addCourseBatch(courses);
    }

    @Override
    public Course queryById(Integer id) {
        return courseDao.queryById(id);
    }


    @Override
    public void addCourse(Course course) {
        courseDao.addCourse(course);
    }

    @Override
    public void deleteCourse(String ids) {
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                courseDao.deleteCourse(Integer.parseInt(id));
            }
        }
    }

    @Override
    public void updateCourse(Course course) {
        courseDao.updateCourse(course);
    }

    @Override
    public List<Course> queryCourseByCondition(Course course) {
        return courseDao.queryCourseByCondition(course);
    }
}
