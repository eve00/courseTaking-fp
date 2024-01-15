package courseTaking.domain

import courseTaking.domain.entity.Application
import valueObject.StudentId

fun getMyApplications(studentId: StudentId): Result<List<Application>> {
    return Result.success(emptyList())
}