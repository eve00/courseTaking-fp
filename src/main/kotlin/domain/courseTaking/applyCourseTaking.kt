package domain.courseTaking

import domain.course.data.loadCourseById
import domain.courseTaking.data.loadAppllicationsByStudentId
import domain.courseTaking.data.saveApplicationInDatabase
import domain.courseTaking.entity.*
import domain.student.data.loadStudentById
import domain.valueObject.CourseId
import domain.valueObject.StudentId
import java.util.*

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