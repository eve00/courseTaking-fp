package courseManagement.data

import valueObject.CourseId
import valueObject.Student

fun loadCourseMembers(courseid: CourseId): Result<List<Student>> {
    return Result.success(
        listOf(Student(), Student())
    )
}



fun saveCourseMembers(members:List<Student>): Result<Unit> {
    return Result.success(Unit)
}