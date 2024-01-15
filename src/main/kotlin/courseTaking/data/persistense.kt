package courseTaking.data

import domain.courseTaking.entity.Application
import domain.courseTaking.entity.ApplicationId
import domain.courseTaking.entity.toApplication
import domain.valueObject.Course
import domain.valueObject.CourseId
import domain.valueObject.Student
import domain.valueObject.StudentId


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