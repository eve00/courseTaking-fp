package domain.courseManagement.entity

import domain.valueObject.Course
import domain.valueObject.Student

sealed class CourseMemberList {
    data class OfVacant(val value: VacantCourseMemberList): CourseMemberList()
    data class OfFull(val value: FullCourseMemberList): CourseMemberList()
    data class OfInvalid(val value: InvalidCourseMemberList): CourseMemberList()
    
    fun addMembers(members: List<Student>) : CourseMemberList =
        when(this@CourseMemberList){
            is OfVacant -> addMembers(members)
            is OfFull -> throw IllegalStateException()
            is OfInvalid -> throw IllegalStateException()
        }

    private fun add(members: List<Student>): CourseMemberList {
        val updatedMembers = this.members + members
        return when {
            updatedMembers.size < course.capacity -> OfVacant(
                VacantCourseMemberList(
                    course,
                    updatedMembers
                )
            )
            updatedMembers.size == course.capacity -> OfFull(
                FullCourseMemberList(
                    course,
                    updatedMembers
                )
            )
            updatedMembers.size > course.capacity -> OfInvalid(
                InvalidCourseMemberList(
                    course,
                    updatedMembers
                )
            )

            else -> {
                OfVacant(VacantCourseMemberList(Course(), listOf()))
            }
        }
    }
    val course: Course
        get() {
            return when (this@CourseMemberList) {
                is OfVacant -> this@CourseMemberList.course
                is OfFull -> this@CourseMemberList.course
                is OfInvalid -> this@CourseMemberList.course
            }
        }
    val members: List<Student>
        get() {
            return when (this@CourseMemberList) {
                is OfVacant -> this@CourseMemberList.members
                is OfFull -> this@CourseMemberList.members
                is OfInvalid -> this@CourseMemberList.members
            }
        }
    fun unwrap(): Any {
        return when (this@CourseMemberList) {
            is OfVacant -> this@CourseMemberList.value
            is OfFull -> this@CourseMemberList.value
            is OfInvalid -> this@CourseMemberList.value
        }
    }
}

data class VacantCourseMemberList(
    val course: Course,
    val members: List<Student>
)

data class FullCourseMemberList(
    val course: Course,
    val members: List<Student>
)

data class InvalidCourseMemberList(
    val course: Course,
    val members: List<Student>
)

