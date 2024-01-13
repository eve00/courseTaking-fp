package domain.courseTaking

import domain.entity.*
import org.http4k.core.Request
import org.jetbrains.annotations.Async

/*
* workflow
* */

suspend fun createApplication(
    applicationId: ApplicationId,
    studentId: StudentId,
    course: Course
): Result<Application.OfUnconfirmed> {
    val unprocessedApplication = Application.OfUnprocessed(UnprocessedApplication(applicationId, studentId, course))
    val applicationList =
        loadAppllicationListFromDb(unprocessedApplication.studentId).getOrElse { return Result.failure(it) }
            .toApplicationList(unprocessedApplication.studentId)
    val updateApplicationListResult = updateApplicationList(applicationList, unprocessedApplication)
    val unconfirmedApplication = unprocessedApplication.toUnconfirmedApplication()
    updateApplicationListResult
        .onSuccess {
            save(unconfirmedApplication).onFailure { return Result.failure(it) }
        }
        .onFailure {
            return Result.failure(it)
        }

    return Result.success(unconfirmedApplication)
}

/*
* domain
* */

fun List<Application>.toApplicationList(studentId: StudentId) =
    ApplicationList(studentId, this)

fun updateApplicationList(
    applicationList: ApplicationList,
    application: Application.OfUnprocessed
): Result<UpdatedApplicationList> {
    return if (checkTotalCredits(applicationList)) {
        Result.success(
            UpdatedApplicationList(
                id = applicationList.id,
                applications = applicationList.applications.plus(listOf(application))
            )
        )
    } else {
        Result.failure(exception = Exception("取得可能な単位数を超過しています。"))
    }
}

fun checkTotalCredits(applicationList: ApplicationList): Boolean {
    return applicationList.applications.map { application: Application ->
        application.course.credit
    }.reduce { acc, credit -> acc + credit } <= 26
}

/*
* DTO
* */


/*
* database
* */

suspend fun loadAppllicationListFromDb(studentId: StudentId): Result<List<Application>> {
    val applications = findByStudentId(studentId) ?: listOf()

    return Result.success(applications)
}

suspend fun save(application: Application): Result<Unit> {
    return Result.success(Unit)
}


fun findByStudentId(studentId: StudentId): List<Application>? {
    return listOf(
        ApplicationFromDb(
            applicationId = ApplicationId(String()),
            studentId = StudentId(String()),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        ),
        ApplicationFromDb(
            applicationId = ApplicationId(String()),
            studentId = StudentId(String()),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        )
    ).map { it.toApplication() }
}

/*
* model
* */
data class ApplicationFromDb(
    val applicationId: ApplicationId,
    val studentId: StudentId,
    val course: Course,
    val state: ApplicationState
)

enum class ApplicationState {
    UNCONFIRMED, CONFIRMED, INVALIDATED
}

fun ApplicationFromDb.toApplication(): Application {
    return when (this.state) {
        ApplicationState.UNCONFIRMED -> Application.OfUnconfirmed(
            UnconfirmedApplication(
                this.applicationId,
                this.studentId,
                this.course
            )
        )

        ApplicationState.CONFIRMED -> Application.OfConfirmed(
            ConfirmedApplication(
                this.applicationId,
                this.studentId,
                this.course
            )
        )

        ApplicationState.INVALIDATED -> Application.OfInvalidated(
            InvalidatedApplication(
                this.applicationId,
                this.studentId,
                this.course
            )
        )
    }
}


