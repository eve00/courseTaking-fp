package domain.courseTaking

import domain.courseTaking.entity.CourseTakingSchedule
import domain.courseTaking.entity.toCourseTakingSchedule
import domain.entity.*
import org.http4k.core.Request
import org.jetbrains.annotations.Async

/*
* workflow
* */

suspend fun createApplication(
    applicationId: ApplicationId,
    student: Student,
    course: Course
): Result<Application.OfUnconfirmed> {
    val unprocessedApplication = Application.OfUnprocessed(UnprocessedApplication(applicationId,student , course))
    val applicationList =
        loadAppllicationListFromDb(unprocessedApplication.student.id).getOrElse { return Result.failure(it) }
            .toCourseTakingSchedule(unprocessedApplication.student)
    val updateCourseTakingScheduleResult = updateCourseTakingSchedule(applicationList, unprocessedApplication)
    val unconfirmedApplication = unprocessedApplication.toUnconfirmedApplication()
    updateCourseTakingScheduleResult
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

fun updateCourseTakingSchedule(
    courseTakingSchedule: CourseTakingSchedule,
    application: Application.OfUnprocessed
): Result<CourseTakingSchedule> {
    val updatedCourseTakingSchedule = courseTakingSchedule.addApplication(application)
    return when(updatedCourseTakingSchedule){
        is CourseTakingSchedule.OfVacant -> Result.success(updatedCourseTakingSchedule)
        is CourseTakingSchedule.OfFull -> Result.success(updatedCourseTakingSchedule)
        is CourseTakingSchedule.OfInvalid -> Result.failure(Exception("取得可能な単位数を超過しています。"))
    }
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
            student = Student(),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        ),
        ApplicationFromDb(
            applicationId = ApplicationId(String()),
            student = Student(),
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
    val student: Student,
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


