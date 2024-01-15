package courseTaking.domain

import courseTaking.data.loadCourseById
import courseTaking.data.loadAppllicationsByStudentId
import courseTaking.data.saveApplicationInDatabase
import courseTaking.domain.entity.*
import valueObject.CourseId
import valueObject.Student
import valueObject.StudentId
import java.util.UUID

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

suspend fun loadStudentById(studentId: StudentId): Student = Student()
