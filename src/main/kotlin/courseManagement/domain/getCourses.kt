package courseManagement.domain

import course.data.Course
import domain.valueObject.Course

fun getCourses():Result<List<Course>>{
    return Result.success(listOf(Course()))
}