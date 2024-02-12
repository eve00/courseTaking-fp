package latestModel.workflow

import latestModel.dataClass.Application
import latestModel.dataClass.RejectedApplication


fun selectApplications(
    applications:List<Application.OfCreated>,
    capacity: Int
): List<Application> {
    val rejectedApplications = applications.subList(capacity,applications.size).map { Application.OfRejected(
        RejectedApplication(it.id,it.student,it.course)
    ) }
    val selectedApplication = applications.subList(0,capacity)

    return selectedApplication + rejectedApplications
}
