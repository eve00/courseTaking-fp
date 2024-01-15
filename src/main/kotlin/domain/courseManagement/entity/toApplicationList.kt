package domain.courseManagement.entity

import domain.entity.Application
import domain.entity.Course

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

