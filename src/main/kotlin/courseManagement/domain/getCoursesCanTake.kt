package courseManagement.domain

import domain.valueObject.Course

fun getCoursesCanTake():Result<List<Course>>{
    return Result.success(emptyList())
}