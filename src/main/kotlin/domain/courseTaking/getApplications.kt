package domain.courseTaking

import domain.courseTaking.entity.Application
import domain.valueObject.StudentId

fun getMyApplications(studentId: StudentId): Result<List<Application>> {
    return Result.success(emptyList())
}