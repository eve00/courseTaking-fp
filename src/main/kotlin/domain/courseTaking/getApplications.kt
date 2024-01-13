package domain.courseTaking

import domain.entity.Application
import domain.entity.ApplicationId
import domain.entity.StudentId
import domain.entity.UnconfirmedApplication

fun getApplications(studentId: StudentId): Result<List<Application>> {
    return Result.success(emptyList())
}