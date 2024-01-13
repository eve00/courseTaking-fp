package domain.courseTaking

import domain.entity.Course

fun getCourses():Result<List<Course>>{
    return Result.success(listOf(Course()))
}