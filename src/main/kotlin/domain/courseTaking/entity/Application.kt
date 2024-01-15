package domain.courseTaking.entity

import domain.courseTaking.data.ApplicationData
import domain.courseTaking.data.ApplicationState
import domain.valueObject.Course
import domain.valueObject.Student
import domain.valueObject.common.Identifier

typealias ApplicationId = Identifier<Application, String>

sealed class Application {
    data class OfUnprocessed(val value: UnprocessedApplication) : Application()

    data class OfUnconfirmed(val value: UnconfirmedApplication) : Application()
    data class OfConfirmed(val value: ConfirmedApplication) : Application()

    /*「落選したこと」を永続化するため*/
    data class OfInvalidated(val value: InvalidatedApplication) : Application()

    val applicationId: ApplicationId
        get() = when (this@Application) {
            is OfUnprocessed -> this@Application.applicationId
            is OfUnconfirmed -> this@Application.applicationId
            is OfConfirmed -> this@Application.applicationId
            is OfInvalidated -> this@Application.applicationId
        }
    val student: Student
        get() = when (this@Application) {
            is OfUnprocessed -> this@Application.student
            is OfUnconfirmed -> this@Application.student
            is OfConfirmed -> this@Application.student
            is OfInvalidated -> this@Application.student
        }
    val course: Course
        get() = when (this@Application) {
            is OfUnprocessed -> this@Application.course
            is OfUnconfirmed -> this@Application.course
            is OfConfirmed -> this@Application.course
            is OfInvalidated -> this@Application.course
        }

    fun unwrap(): Any = when (this@Application) {
        is OfUnprocessed -> this@Application.value
        is OfUnconfirmed -> this@Application.value
        is OfConfirmed -> this@Application.value
        is OfInvalidated -> this@Application.value
    }
    /*state transition*/
    fun toUnconfirmedApplication(): Application = when (this@Application) {
        is OfUnprocessed -> toUnconfirmedApplication()
        is OfUnconfirmed -> throw IllegalStateException()
        is OfConfirmed -> throw IllegalStateException()
        is OfInvalidated -> throw IllegalStateException()
    }
    fun toConfirmedApplication(): Application = when (this@Application) {
        is OfUnprocessed -> throw IllegalStateException()
        is OfUnconfirmed -> toConfirmedApplication()
        is OfConfirmed -> throw IllegalStateException()
        is OfInvalidated -> throw IllegalStateException()
    }
    fun toInvalidatedApplication(): Application = when (this@Application) {
        is OfUnprocessed -> throw IllegalStateException()
        is OfUnconfirmed -> toInvalidatedApplication()
        is OfConfirmed -> throw IllegalStateException()
        is OfInvalidated -> throw IllegalStateException()
    }

    /*implement operation*/
    private fun toUnconfirmed(): OfUnconfirmed = OfUnconfirmed(UnconfirmedApplication(this.applicationId, this.student, this.course))
    private fun toConfirmed(): OfConfirmed = OfConfirmed(ConfirmedApplication(this.applicationId, this.student, this.course))
    private fun toInvalidated(): OfInvalidated = OfInvalidated(InvalidatedApplication(this.applicationId, this.student, this.course))
}

data class UnprocessedApplication(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course
)

data class UnconfirmedApplication(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course
)

data class ConfirmedApplication(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course
)

data class InvalidatedApplication(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course
)

fun ApplicationData.toApplication(): Application {
    return when (this.state) {
        ApplicationState.UNCONFIRMED -> Application.OfUnconfirmed(
            UnconfirmedApplication(
                this.applicationId,
                this.student,
                this.course
            )
        )

        ApplicationState.CONFIRMED -> Application.OfConfirmed(
            ConfirmedApplication(
                this.applicationId,
                this.student,
                this.course
            )
        )

        ApplicationState.INVALIDATED -> Application.OfInvalidated(
            InvalidatedApplication(
                this.applicationId,
                this.student,
                this.course
            )
        )
    }
}
