package courseManagement.domain

import course.data.loadCourseById
import domain.courseManagement.entity.toApplicationList
import domain.courseManagement.entity.toCourseMemberList
import domain.courseTaking.data.loadApplicationsByCourseId
import domain.courseTaking.data.saveApplicationInDatabase
import domain.courseTaking.entity.Application
import domain.valueObject.CourseId

fun register(courseId: CourseId):Result<Unit>{
    val course = loadCourseById(courseId)
    //load
    val applicationList = loadApplicationsByCourseId(courseId).getOrElse { return Result.failure(it) }
        .toApplicationList(course)
    val courseMemberList = courseManagement.data.loadCourseMembers(courseId).getOrElse { return Result.failure(it) }
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
    courseManagement.data.saveCourseMembers(updatedCourseMemberList.members)

    return Result.success(Unit)
}