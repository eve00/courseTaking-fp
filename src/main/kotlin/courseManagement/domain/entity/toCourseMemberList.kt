package courseManagement.domain.entity

import course.data.Course
import courseManagement.domain.valueObject.Student
import valueObject.Student

fun List<Student>.toCourseMemberList(course: Course): CourseMemberList {
    return when {
        this.size < course.capacity -> CourseMemberList.OfVacant(
            VacantCourseMemberList(
                course,
                this
            )
        )
        this.size == course.capacity -> CourseMemberList.OfFull(
            FullCourseMemberList(
                course,
                this
            )
        )
        this.size > course.capacity -> CourseMemberList.OfInvalid(
            InvalidCourseMemberList(
                course,
                this
            )
        )
        else -> {
            CourseMemberList.OfInvalid(InvalidCourseMemberList(Course(), listOf()))
        }
    }
}
