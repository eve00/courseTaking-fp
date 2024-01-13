package domain.courseTaking

import domain.entity.Course

fun getCoursesCanTake():Result<List<Course>>{
    return Result.success(emptyList())
}