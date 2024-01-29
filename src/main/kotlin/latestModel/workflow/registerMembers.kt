package latestModel.workflow

import latestModel.dataClass.NotRegisteredApplication
import latestModel.dataClass.RegisteredApplication

fun registerApplication(
    applications: List<NotRegisteredApplication>
): List<RegisteredApplication> {

    return applications.map { RegisteredApplication(it.id, it.student, it.course) }
}
