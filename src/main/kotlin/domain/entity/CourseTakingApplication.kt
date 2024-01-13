package domain.entity

import domain.entity.common.Identifier

typealias ApplicationId = Identifier<Application, String>

sealed class Application {
    data class OfUnprocessed(val value: UnprocessedApplication) : Application() {
        fun toUnconfirmedApplication():OfUnconfirmed {
            return OfUnconfirmed(UnconfirmedApplication(value.applicationId,value.studentId,value.course))
        }
    }
    data class OfUnconfirmed(val value: UnconfirmedApplication) : Application(){
        fun toConfirmedApplication():OfConfirmed {
            return OfConfirmed(ConfirmedApplication(value.applicationId,value.studentId,value.course))
        }
        fun toInvalidatedApplication():OfInvalidated {
            return OfInvalidated(InvalidatedApplication(value.applicationId,value.studentId,value.course))
        }
    }
    data class OfConfirmed(val value: ConfirmedApplication) : Application()
    data class OfInvalidated(val value: InvalidatedApplication) : Application()

    val applicationId: ApplicationId
        get() {
            return when (this@Application) {
                is Application.OfUnprocessed -> this@Application.applicationId
                is Application.OfUnconfirmed -> this@Application.applicationId
                is Application.OfConfirmed -> this@Application.applicationId
                is Application.OfInvalidated -> this@Application.applicationId
            }
        }
    val studentId: StudentId
        get() {
            return when (this@Application) {
                is Application.OfUnprocessed -> this@Application.studentId
                is Application.OfUnconfirmed -> this@Application.studentId
                is Application.OfConfirmed -> this@Application.studentId
                is Application.OfInvalidated -> this@Application.studentId
            }
        }
    val course: Course
        get() {
            return when (this@Application) {
                is Application.OfUnprocessed -> this@Application.course
                is Application.OfUnconfirmed -> this@Application.course
                is Application.OfConfirmed -> this@Application.course
                is Application.OfInvalidated -> this@Application.course
            }
        }
    fun unwrap(): Any {
        return when (this@Application) {
            is Application.OfUnprocessed -> this@Application.value
            is Application.OfUnconfirmed -> this@Application.value
            is Application.OfConfirmed -> this@Application.value
            is Application.OfInvalidated -> this@Application.value
        }
    }
}

data class UnprocessedApplication(
    val applicationId: ApplicationId,
    val studentId: StudentId,
    val course: Course
)

data class UnconfirmedApplication(
    val applicationId: ApplicationId,
    val studentId: StudentId,
    val course: Course
)

data class ConfirmedApplication(
    val applicationId: ApplicationId,
    val studentId: StudentId,
    val course: Course
)

data class InvalidatedApplication(
    val applicationId: ApplicationId,
    val studentId: StudentId,
    val course: Course
)