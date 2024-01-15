package domain.courseTaking

import domain.courseManagement.loadCourseById
import domain.courseTaking.entity.CourseTakingSchedule
import domain.courseTaking.entity.toCourseTakingSchedule
import domain.entity.*
import java.util.UUID

/*
* workflow
* */

suspend fun createApplication(
    studentId: StudentId,
    courseId: CourseId
): Result<Unit> {

    //load
    val student = loadStudentById(studentId)
    val course = loadCourseById(courseId)
    val courseTakingSchedule =
        loadAppllicationsByStudentId(studentId).getOrElse { return Result.failure(it) }
            .toCourseTakingSchedule(student)

    //Applicationの作成
    val newApplication = Application.OfUnconfirmed(
        UnconfirmedApplication(
            ApplicationId(UUID.randomUUID().toString()),
            student,
            course
        )
    )
    //CourseTakingScheduleの更新（add
    val updatedCourseTakingSchedule = courseTakingSchedule.addApplication(newApplication)
    when (updatedCourseTakingSchedule) {
        is CourseTakingSchedule.OfVacant -> {}
        is CourseTakingSchedule.OfFull -> {}
        is CourseTakingSchedule.OfInvalid -> return Result.failure(Exception("取得可能な単位数を超過しています。"))
    }

    //save
    saveApplicationInDatabase(newApplication)
    return Result.success(Unit)
}

/*
* domain
* */


/*
* DTO
* */


/*
* database
* */

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

suspend fun loadStudentById(studentId: StudentId): Student = Student()

suspend fun saveApplicationInDatabase(application: Application): Result<Unit> {
    return Result.success(Unit)
}


/*
* model
* */
data class ApplicationData(
    val applicationId: ApplicationId,
    val student: Student,
    val course: Course,
    val state: ApplicationState
)

enum class ApplicationState {
    UNCONFIRMED, CONFIRMED, INVALIDATED
}

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


