package domain.courseManagement

import domain.valueObject.Course

fun getCourses():Result<List<Course>>{
    return Result.success(listOf(Course()))
}