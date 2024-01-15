package domain.courseTaking

import domain.courseTaking.entity.toCourseTakingSchedule
import domain.entity.*
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Status
import java.nio.file.Files.delete

/*
* workflow
* */

suspend fun deleteMyApplication(applicationId:ApplicationId): Result<Unit> {
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


/*
* database
* */
suspend fun deleteApplicationfromDB(applicationId: ApplicationId):Result<Unit>{
    return Result.success(Unit)
}

suspend fun loadApplicationById(applicationId: ApplicationId):Application{
    return ApplicationData(
        applicationId = ApplicationId(String()),
        student = Student(),
        course = Course(),
        state = ApplicationState.UNCONFIRMED
    ).toApplication()
}