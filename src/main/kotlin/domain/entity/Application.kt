package domain.entity

import domain.entity.common.Identifier

typealias ApplicationId = Identifier<Application, String>

sealed class Application {
    data class OfUnprocessed(val value: UnprocessedApplication) : Application() {
        fun toUnconfirmedApplication(): OfUnconfirmed {
            return OfUnconfirmed(UnconfirmedApplication(value.applicationId, value.student, value.course))
        }
    }

    data class OfUnconfirmed(val value: UnconfirmedApplication) : Application() {
        fun toConfirmedApplication(): OfConfirmed {
            return OfConfirmed(ConfirmedApplication(value.applicationId, value.student, value.course))
        }

        fun toInvalidatedApplication(): OfInvalidated {
            return OfInvalidated(InvalidatedApplication(value.applicationId, value.student, value.course))
        }
    }

    data class OfConfirmed(val value: ConfirmedApplication) : Application()
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