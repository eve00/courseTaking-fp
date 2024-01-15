package courseTaking.domain

import domain.courseTaking.data.deleteApplicationfromDB
import domain.courseTaking.data.loadApplicationById
import domain.courseTaking.data.loadAppllicationsByStudentId
import domain.courseTaking.entity.ApplicationId
import domain.courseTaking.entity.toCourseTakingSchedule


suspend fun deleteMyApplication(applicationId: ApplicationId): Result<Unit> {
    val application = loadApplicationById(applicationId)
    //courseTakingScheduleをload
    val courseTakingSchedule =
        loadAppllicationsByStudentId(application.student.id).getOrElse { return Result.failure(it) }
            .toCourseTakingSchedule(application.student)
    //courseTakingScheduleを更新
    val updatedCourseTakingSchedule = courseTakingSchedule.removeApplication(application)

    //applicationの削除
    deleteApplicationfromDB(applicationId)

    return Result.success(Unit)
}

