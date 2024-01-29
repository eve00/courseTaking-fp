package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.NotRegisteredApplication
import latestModel.dataClass.RejectedApplication

fun selectApplication(
    applications:List<NotRegisteredApplication>,
    capacity: Int
): List<Application> {
    val rejectedApplications = applications.subList(capacity,applications.size).map { RejectedApplication(it.id,it.student,it.course) }
    val selectedApplication = applications.subList(0,capacity)

    return selectedApplication + rejectedApplications
}
