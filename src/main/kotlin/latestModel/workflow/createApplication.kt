package latestModel.workflow

import latestModel.dataClass.Course
import latestModel.dataClass.NotRegisteredApplication
import latestModel.dataClass.Student

const val MAX_CREDITS = 24
fun createApplication(
    student: Student,
    course: Course,
    nowTotalCredits:Int,
): Result<NotRegisteredApplication> {
    if (nowTotalCredits<= MAX_CREDITS) {
        return Result.success(NotRegisteredApplication("applicationId", student, course))
    } else {
        return Result.failure(Exception("最大取得単位数を超過"))
    }
}

fun createApplicationAsFirstServe(
    student: Student,
    course: Course,
    nowTotalCredits:Int,
    checkCanApplyAsFirstserve: () -> Boolean
): Result<NotRegisteredApplication> {
    if (checkCanApplyAsFirstserve()) {
        return createApplication(student, course, nowTotalCredits)
    } else {
        return Result.failure(Exception("満員"))
    }
}

