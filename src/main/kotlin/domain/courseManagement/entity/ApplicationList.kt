package domain.courseManagement.entity

import domain.courseTaking.entity.Application
import domain.valueObject.*

sealed class ApplicationList {
    data class OfVacant(val value: VacantApplicationList) : ApplicationList()
    data class OfFull(val value: FullApplicationList) : ApplicationList()
    data class OfInvalid(val value: InvalidApplicationList) : ApplicationList()

    /*state transition*/
    fun addApplication(application: Application): ApplicationList =
        when (this@ApplicationList) {
            is OfVacant -> add(application)
            is OfFull -> throw IllegalStateException()
            is OfInvalid -> throw IllegalStateException()
        }

    fun removeApplication(application: Application): ApplicationList =
        when (this@ApplicationList) {
            is OfVacant -> remove(application)
            is OfFull -> remove(application)
            is OfInvalid -> throw IllegalStateException()
        }

    /*implement operation*/
    private fun add(application: Application): ApplicationList {
        val updatedApplications = this.applications + application
        return when {
            updatedApplications.size < course.capacity -> OfVacant(
                VacantApplicationList(
                    course,
                    updatedApplications
                )
            )

            updatedApplications.size == course.capacity -> OfFull(
                FullApplicationList(
                    course,
                    updatedApplications
                )
            )

            updatedApplications.size > course.capacity -> OfInvalid(
                InvalidApplicationList(
                    course,
                    updatedApplications
                )
            )

            else -> {
                OfVacant(VacantApplicationList(Course(), listOf()))
            }
        }
    }

    private fun remove(application: Application): ApplicationList {
        val updatedApplications = this.applications - application
        return OfVacant(VacantApplicationList(this.course, updatedApplications))
    }

    val course: Course
        get() = when (this@ApplicationList) {
            is OfVacant -> this@ApplicationList.course
            is OfFull -> this@ApplicationList.course
            is OfInvalid -> this@ApplicationList.course

        }
    val applications: List<Application>
        get() = when (this@ApplicationList) {
            is OfVacant -> this@ApplicationList.applications
            is OfFull -> this@ApplicationList.applications
            is OfInvalid -> this@ApplicationList.applications
        }

    fun unwrap(): Any = when (this@ApplicationList) {
        is OfVacant -> this@ApplicationList.value
        is OfFull -> this@ApplicationList.value
        is OfInvalid -> this@ApplicationList.value
    }
}

data class VacantApplicationList(
    val course: Course,
    val applications: List<Application>
)

data class FullApplicationList(
    val course: Course,
    val applications: List<Application>
)

data class InvalidApplicationList(
    val course: Course,
    val applications: List<Application>
)
