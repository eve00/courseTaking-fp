package domain.courseTaking

import domain.entity.Application
import domain.entity.ApplicationId
import domain.entity.Student
import domain.entity.StudentId
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Status
import java.nio.file.Files.delete

/*
* workflow
* */

suspend fun deleteApplication(applicationId:ApplicationId): Result<Unit> {
    return delete(applicationId)
}


/*
* database
* */
fun delete(applicationId: ApplicationId):Result<Unit>{
    return Result.success(Unit)
}

