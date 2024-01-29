package latestModel.useCase

import latestModel.dataClass.RejectedApplication
import latestModel.dataStore.ApplicationsDataStore
import latestModel.dataStore.CourseMembersDataStore
import latestModel.workflow.registerApplication
import latestModel.workflow.selectApplication

fun selectAndRegister(
    courseId: String,
    capacity: Int,
    applicationsDataStore: ApplicationsDataStore,
    courseMembersDataStore: CourseMembersDataStore

){
    /*IO*/
    val notRegisteredApplication = applicationsDataStore.findByCourseId(courseId)

    /*workflow*/
    val selectedApplication = selectApplication(notRegisteredApplication,capacity)
    val registeredApplication = registerApplication(notRegisteredApplication)

    /*IO*/
    val rejectedApplication = selectedApplication.filter{it is RejectedApplication}
    applicationsDataStore.save(registeredApplication + rejectedApplication)
    val members = registeredApplication.map { it.student }
    courseMembersDataStore.save(members)
}