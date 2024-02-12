package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.CreatedApplication


fun cancelApplication(
    application: Application,
    checkIsCancelable: () -> Boolean,
): Result<Application.OfCreated> {
    if (checkIsCancelable()) {
        return Result.success(Application.OfCreated(CreatedApplication(application.id, application.student, application.course)))
    } else {
        return Result.failure(Exception("最大取得単位数を超過"))
    }
}