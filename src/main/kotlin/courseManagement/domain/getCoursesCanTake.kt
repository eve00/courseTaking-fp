package courseManagement.domain

import valueObject.Course


fun getCoursesCanTake():Result<List<Course>>{
    return Result.success(emptyList())
}