package domain.courseManagement.entity

import domain.courseManagement.entity.CourseMemberList.*
import domain.entity.Course
import domain.entity.Student

fun List<Student>.toCourseMemberList(course: Course): CourseMemberList {
    return when {
        this.size < course.capacity -> OfVacant(
            VacantCourseMemberList(
                course,
                this
            )
        )
        this.size == course.capacity -> OfFull(
            FullCourseMemberList(
                course,
                this
            )
        )
        this.size > course.capacity -> OfInvalid(
            InvalidCourseMemberList(
                course,
                this
            )
        )
        else -> {
            OfInvalid(InvalidCourseMemberList(Course(), listOf()))
        }
    }
}
