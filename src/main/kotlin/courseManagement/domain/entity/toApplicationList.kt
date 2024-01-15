package courseManagement.domain.entity

import course.data.Course
import courseTaking.domain.entity.Application
import valueObject.Course

/*
* factory
* */
fun List<Application>.toApplicationList(course: Course): ApplicationList {
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

