package domain.courseManagement

import domain.courseManagement.entity.toApplicationList
import domain.courseManagement.entity.toCourseMemberList
import domain.courseTaking.ApplicationData
import domain.courseTaking.ApplicationState
import domain.courseTaking.toApplication
import domain.entity.*

/*
* workflow
* */

suspend fun drawAndRegister(courseId: CourseId): Result<Unit> {
    //load
    val course = loadCourseById(courseId)
    val applicationList = loadApplicationsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toApplicationList(course)
    val courseMemberList = loadStudentsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toCourseMemberList(course)

    //drawを実行する
    val updatedApplicationsToDraw = drawApplication(applicationList.applications, course.capacity)
    //ApplicationListを更新 (remove
    val updatedApplicationList = updatedApplicationsToDraw.filter { it is Application.OfInvalidated }.fold(applicationList){
        acc, application -> acc.removeApplication(application)
    }

    //Applicationを更新 (Confirm
    val updatedApplicationsToConfirm =
        updatedApplicationsToDraw.filter { it is Application.OfInvalidated } +
                updatedApplicationsToDraw.filter { it is Application.OfUnconfirmed }
                    .map { it.toConfirmedApplication() }
    //courseMemberListを更新
    val updatedCourseMemberList =
        courseMemberList.addMembers(updatedApplicationsToConfirm.filter { it is Application.OfConfirmed }
            .map { it.student })

    //save
    updatedApplicationList.applications.map { saveApplicationInDatabase(it) }
    saveCourseMembers(updatedCourseMemberList.members)
    return Result.success(Unit)
}

private fun drawApplication(applications: List<Application>, capacity: Int): List<Application> {
    /*抽選形式の実装*/
    val drawedApplication = applications.shuffled().subList(0, capacity)
    val invalidatedApplications =
        applications.shuffled().subList(capacity, applications.size).map { it.toInvalidatedApplication() }
    return drawedApplication + invalidatedApplications
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

fun loadStudentsByCourseId(courseid: CourseId): Result<List<Student>> {
    return Result.success(
        listOf(Student(), Student())
    )
}

fun saveApplicationInDatabase(application:Application): Result<Unit> {
    return Result.success(Unit)
}
fun saveCourseMembers(members:List<Student>): Result<Unit> {
    return Result.success(Unit)
}

fun loadCourseById(courseId: CourseId): Course = Course()