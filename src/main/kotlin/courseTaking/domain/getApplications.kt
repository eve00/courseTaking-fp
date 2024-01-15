package courseTaking.domain

import courseTaking.domain.valueObject.StudentId
import domain.courseTaking.entity.Application

fun getMyApplications(studentId: StudentId): Result<List<Application>> {
    return Result.success(emptyList())
}