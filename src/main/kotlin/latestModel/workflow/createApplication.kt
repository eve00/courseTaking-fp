package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.Course
import latestModel.dataClass.CreatedApplication
import latestModel.dataClass.Student


fun createApplication(
    student: Student,
    course: Course,
    checkTotalCredits: () -> Boolean,
): Result<Application.OfCreated> {
    if (checkTotalCredits()) {
        return Result.success(Application.OfCreated(CreatedApplication("applicationId", student, course)))
    } else {
        return Result.failure(Exception("最大取得単位数を超過"))
    }
}



