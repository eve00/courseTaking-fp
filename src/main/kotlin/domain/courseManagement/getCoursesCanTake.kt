package domain.courseManagement

import domain.valueObject.Course

fun getCoursesCanTake():Result<List<Course>>{
    return Result.success(emptyList())
}