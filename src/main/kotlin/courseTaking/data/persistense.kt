package courseTaking.data

import courseTaking.domain.entity.Application
import courseTaking.domain.entity.ApplicationId
import courseTaking.domain.entity.toApplication
import valueObject.Course
import valueObject.CourseId
import valueObject.Student
import valueObject.StudentId

data class ApplicationData(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course,
    val state: ApplicationState
)

enum class ApplicationState {
    UNCONFIRMED, CONFIRMED, INVALIDATED
}
suspend fun loadAppllicationsByStudentId(studentId: StudentId): Result<List<Application>> {
    return Result.success(listOf(
        ApplicationData(
            applicationId = ApplicationId(String()),
            student = Student(),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        ),
        ApplicationData(
            applicationId = ApplicationId(String()),
            student = Student(),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        )
    ).map { it.toApplication() })
}

fun loadApplicationsByCourseId(courseid: CourseId): Result<List<Application>> {
    return Result.success(listOf(
        ApplicationData(
            applicationId = ApplicationId(String()),
            student = Student(),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        ),
        ApplicationData(
            applicationId = ApplicationId(String()),
            student = Student(),
            course = Course(),
            state = ApplicationState.UNCONFIRMED
        )
    ).map { it.toApplication() })
}

suspend fun loadApplicationById(applicationId: ApplicationId): Application {
    return ApplicationData(
        applicationId = ApplicationId(String()),
        student = Student(),
        course = Course(),
        state = ApplicationState.UNCONFIRMED
    ).toApplication()
}

fun saveApplicationInDatabase(application: Application): Result<Unit> {
    return Result.success(Unit)
}

suspend fun deleteApplicationfromDB(applicationId: ApplicationId):Result<Unit>{
    return Result.success(Unit)
}