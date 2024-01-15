package domain.entity

import domain.entity.common.Identifier
import sun.jvm.hotspot.oops.CellTypeState.value

typealias ApplicationId = Identifier<Application, String>

sealed class Application {
    data class OfUnprocessed(val value: UnprocessedApplication) : Application()

    data class OfUnconfirmed(val value: UnconfirmedApplication) : Application()
    data class OfConfirmed(val value: ConfirmedApplication) : Application()

    /*「落選したこと」を永続化するため*/
    data class OfInvalidated(val value: InvalidatedApplication) : Application()

    val applicationId: ApplicationId
        get() = when (this@Application) {
            is Application.OfUnprocessed -> this@Application.applicationId
            is Application.OfUnconfirmed -> this@Application.applicationId
            is Application.OfConfirmed -> this@Application.applicationId
            is Application.OfInvalidated -> this@Application.applicationId
        }
    val student: Student
        get() = when (this@Application) {
            is Application.OfUnprocessed -> this@Application.student
            is Application.OfUnconfirmed -> this@Application.student
            is Application.OfConfirmed -> this@Application.student
            is Application.OfInvalidated -> this@Application.student
        }
    val course: Course
        get() = when (this@Application) {
            is Application.OfUnprocessed -> this@Application.course
            is Application.OfUnconfirmed -> this@Application.course
            is Application.OfConfirmed -> this@Application.course
            is Application.OfInvalidated -> this@Application.course
        }

    fun unwrap(): Any = when (this@Application) {
        is Application.OfUnprocessed -> this@Application.value
        is Application.OfUnconfirmed -> this@Application.value
        is Application.OfConfirmed -> this@Application.value
        is Application.OfInvalidated -> this@Application.value
    }
    /*state transition*/
    fun toUnconfirmedApplication():Application = when (this@Application) {
        is Application.OfUnprocessed -> toUnconfirmedApplication()
        is Application.OfUnconfirmed -> throw IllegalStateException()
        is Application.OfConfirmed -> throw IllegalStateException()
        is Application.OfInvalidated -> throw IllegalStateException()
    }
    fun toConfirmedApplication():Application = when (this@Application) {
        is Application.OfUnprocessed -> throw IllegalStateException()
        is Application.OfUnconfirmed -> toConfirmedApplication()
        is Application.OfConfirmed -> throw IllegalStateException()
        is Application.OfInvalidated -> throw IllegalStateException()
    }
    fun toInvalidatedApplication():Application = when (this@Application) {
        is Application.OfUnprocessed -> throw IllegalStateException()
        is Application.OfUnconfirmed -> toInvalidatedApplication()
        is Application.OfConfirmed -> throw IllegalStateException()
        is Application.OfInvalidated -> throw IllegalStateException()
    }

    /*implement operation*/
    private fun toUnconfirmed():OfUnconfirmed = OfUnconfirmed(UnconfirmedApplication(this.applicationId, this.student, this.course))
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