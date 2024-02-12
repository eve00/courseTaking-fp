package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.RegisteredApplication

fun registerApplications(
    applications: List<Application.OfCreated>
): List<Application.OfRegistered> {

    return applications.map { Application.OfRegistered(RegisteredApplication(it.id, it.student, it.course)) }
}
