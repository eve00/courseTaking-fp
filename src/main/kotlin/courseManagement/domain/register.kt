package courseManagement.domain

import courseManagement.data.loadCourseById
import courseManagement.domain.entity.toApplicationList
import courseManagement.domain.entity.toCourseMemberList
import courseTaking.data.loadApplicationsByCourseId
import courseTaking.data.saveApplicationInDatabase
import courseTaking.domain.entity.Application
import valueObject.CourseId


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

