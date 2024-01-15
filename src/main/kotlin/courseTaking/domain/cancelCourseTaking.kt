package courseTaking.domain

import courseTaking.data.deleteApplicationfromDB
import courseTaking.data.loadApplicationById
import courseTaking.data.loadAppllicationsByStudentId
import courseTaking.domain.entity.ApplicationId
import courseTaking.domain.entity.toCourseTakingSchedule


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

