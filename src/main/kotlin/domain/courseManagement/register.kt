package domain.courseManagement

import domain.courseManagement.entity.toApplicationList
import domain.courseManagement.entity.toCourseMemberList
import domain.entity.Application
import domain.entity.CourseId

fun register(courseId: CourseId):Result<Unit>{
    val course = loadCourseById(courseId)
    //load
    val applicationList = loadApplicationsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toApplicationList(course)
    val courseMemberList = loadStudentsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toCourseMemberList(course)

    //Applicationを更新 (Confirm
    val updatedApplicationsToConfirm =
                applicationList.applications.filter { it is Application.OfUnconfirmed }
                    .map { it.toConfirmedApplication() }
    //courseMemberListを更新
    val updatedCourseMemberList =
        courseMemberList.addMembers(updatedApplicationsToConfirm.filter { it is Application.OfConfirmed }
            .map { it.student })

    //save
    updatedApplicationsToConfirm.map { saveApplicationInDatabase(it) }
    saveCourseMembers(updatedCourseMemberList.members)

    return Result.success(Unit)
}