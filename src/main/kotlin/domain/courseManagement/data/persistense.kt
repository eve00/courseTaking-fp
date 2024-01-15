package domain.courseManagement.data

import domain.valueObject.CourseId
import domain.valueObject.Student

fun loadCourseMembers(courseid: CourseId): Result<List<Student>> {
    return Result.success(
        listOf(Student(), Student())
    )
}



fun saveCourseMembers(members:List<Student>): Result<Unit> {
    return Result.success(Unit)
}