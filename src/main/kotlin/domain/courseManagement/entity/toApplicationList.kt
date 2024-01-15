package domain.courseManagement.entity

import domain.courseTaking.entity.Application
import domain.valueObject.Course

/*
* factory
* */
fun List<Application>.toApplicationList(course: Course):ApplicationList {
    return when {
        this.size < course.capacity -> ApplicationList.OfVacant(
            VacantApplicationList(
                course,
                this
            )
        )
        this.size == course.capacity -> ApplicationList.OfFull(
            FullApplicationList(
                course,
                this
            )
        )
        this.size > course.capacity -> ApplicationList.OfInvalid(
            InvalidApplicationList(
                course,
                this
            )
        )
        else -> {
            ApplicationList.OfInvalid(InvalidApplicationList(Course(), listOf()))
        }
    }
}

