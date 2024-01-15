package courseManagement.domain

import courseManagement.data.loadCourseById
import courseManagement.domain.entity.toApplicationList
import courseManagement.domain.entity.toCourseMemberList
import courseTaking.data.loadApplicationsByCourseId
import courseTaking.data.saveApplicationInDatabase
import courseTaking.domain.entity.Application
import valueObject.CourseId

suspend fun drawAndRegister(courseId: CourseId): Result<Unit> {
    //load
    val course = loadCourseById(courseId)
    val applicationList = loadApplicationsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toApplicationList(course)
    val courseMemberList = courseManagement.data.loadCourseMembers(courseId).getOrElse { return Result.failure(it) }
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
    courseManagement.data.saveCourseMembers(updatedCourseMemberList.members)
    return Result.success(Unit)
}

private fun drawApplication(applications: List<Application>, capacity: Int): List<Application> {
    /*抽選形式の実装*/
    val drawedApplication = applications.shuffled().subList(0, capacity)
    val invalidatedApplications =
        applications.shuffled().subList(capacity, applications.size).map { it.toInvalidatedApplication() }
    return drawedApplication + invalidatedApplications
}

