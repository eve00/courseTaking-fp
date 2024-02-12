package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.Course
import latestModel.dataClass.CreatedApplication
import latestModel.dataClass.Student

fun createApplicationWithFirstArrival(
    student: Student,
    course: Course,
    checkTotalCredits: () -> Boolean,
    checkCanApplyWithFirstArrival: () -> Boolean
): Result<Application.OfCreated> {
    if (checkCanApplyWithFirstArrival()) {
        if (checkTotalCredits()) {
            return Result.success(Application.OfCreated(CreatedApplication("applicationId", student, course)))
        } else {
            return Result.failure(Exception("最大取得単位数を超過"))
        }    } else {
        return Result.failure(Exception("満員"))
    }
}