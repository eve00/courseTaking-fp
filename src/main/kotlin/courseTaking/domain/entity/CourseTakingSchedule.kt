package courseTaking.domain.entity

import domain.valueObject.DowAndPeriod
import domain.valueObject.Student


sealed class CourseTakingSchedule {
    data class OfVacant(val value: VacantCourseTakingSchedule) : CourseTakingSchedule()
    data class OfFull(val value: FullCourseTakingSchedule) : CourseTakingSchedule()
    data class OfInvalid(val value: InvalidCourseTakingSchedule) : CourseTakingSchedule()

    val student: Student
        get() = when (this@CourseTakingSchedule) {
            is OfVacant -> this@CourseTakingSchedule.student
            is OfFull -> this@CourseTakingSchedule.student
            is OfInvalid -> this@CourseTakingSchedule.student
        }
    val applications: Map<DowAndPeriod, Application>
        get() = when (this@CourseTakingSchedule) {
            is OfVacant -> this@CourseTakingSchedule.applications
            is OfFull -> this@CourseTakingSchedule.applications
            is OfInvalid -> this@CourseTakingSchedule.applications
        }

    fun unwrap(): Any = when (this@CourseTakingSchedule) {
        is OfVacant -> this@CourseTakingSchedule.value
        is OfFull -> this@CourseTakingSchedule.value
        is OfInvalid -> this@CourseTakingSchedule.value
    }

    /*state transition*/
    fun addApplication(application: Application): CourseTakingSchedule =
        when (this@CourseTakingSchedule) {
            is OfVacant -> add(application)
            is OfFull -> throw IllegalStateException()
            is OfInvalid -> throw IllegalStateException()
        }

    fun removeApplication(application: Application): CourseTakingSchedule =
        when (this@CourseTakingSchedule) {
            is OfVacant -> remove(application)
            is OfFull -> remove(application)
            is OfInvalid -> throw IllegalStateException()
        }

    /*implement operation*/
    private fun add(application: Application): CourseTakingSchedule {
        val updatedApplications = this.applications + mapOf(application.course.dowAndPeriod to application)
        val updatedTotalCredit = updatedApplications.map { it.value.course.credit }
            .reduce { acc, credit -> acc + credit }
        return when {
            updatedTotalCredit < student.maxCredit -> OfVacant(VacantCourseTakingSchedule(student, updatedApplications))
            updatedTotalCredit == student.maxCredit -> OfFull(FullCourseTakingSchedule(student, updatedApplications))
            updatedTotalCredit > student.maxCredit -> OfInvalid(
                InvalidCourseTakingSchedule(
                    student,
                    updatedApplications
                )
            )

            else -> {
                OfVacant(VacantCourseTakingSchedule(Student(), mapOf()))
            }
        }
    }

    private fun remove(application: Application): CourseTakingSchedule {
        val updatedApplications = this.applications.minus(application.course.dowAndPeriod)
        return OfVacant(VacantCourseTakingSchedule(this.student, updatedApplications))
    }

}

data class VacantCourseTakingSchedule(
    val student: Student,
    val applications: Map<DowAndPeriod, Application>
)

data class FullCourseTakingSchedule(
    val student: Student,
    val applications: Map<DowAndPeriod, Application>
)

data class InvalidCourseTakingSchedule(
    val student: Student,
    val applications: Map<DowAndPeriod, Application>
)

