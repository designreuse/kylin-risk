package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.CourseDao;
import com.rkylin.risk.core.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Repository("courseDao")
public class CourseDaoImpl extends BaseDaoImpl<Course> implements CourseDao {

    @Override
    public List<Course> queryByUserrelatedid(String userrelatedid) {
        return super.queryList("queryByUserrelatedid", userrelatedid);
    }

    @Override
    public List<String> queryCourseNameByUserrelatedid(String userrelatedid) {
        return super.queryList("queryCourseNameByUserrelatedid", userrelatedid);
    }

    @Override
    public List<Course> queryCourseByCondition(Course course) {
        return super.queryList("queryCourse", course);
    }

    @Override
    public Course queryByMerchantidAndCourseName(String merchantid, String coursename) {
        Map map = new HashMap();
        map.put("merchantid", merchantid);
        map.put("coursename", coursename);
        return super.queryOne("queryByMerchantidAndCourseName", map);
    }

    @Override
    public void addCourseBatch(List<Course> courses) {
        super.addBatch("addCourseBatch", courses);
    }

    @Override
    public Course queryById(Integer id) {
        return super.get(id);
    }

    @Override
    public void addCourse(Course course) {
        super.add(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        super.del(id);
    }

    @Override
    public void updateCourse(Course course) {
        super.modify(course);
    }
}
