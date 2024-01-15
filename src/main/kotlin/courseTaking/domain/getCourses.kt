package courseTaking.domain

import valueObject.Course


fun getCourses():Result<List<Course>>{
    return Result.success(listOf(Course()))
}