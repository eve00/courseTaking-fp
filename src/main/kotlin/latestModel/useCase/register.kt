package latestModel.useCase

import latestModel.dataStore.ApplicationsDataStore
import latestModel.dataStore.CourseMembersDataStore
import latestModel.workflow.registerApplication


fun register(
    courseId: String,
    capacity: Int,
    applicationsDataStore: ApplicationsDataStore,
    courseMembersDataStore: CourseMembersDataStore
){
    /*IO*/
    val notRegisteredApplication = applicationsDataStore.findByCourseId(courseId)

    /*workflow*/
    val registeredApplication = registerApplication(notRegisteredApplication)

    /*IO*/
    applicationsDataStore.save(registeredApplication)
    val members = registeredApplication.map { it.student }
    courseMembersDataStore.save(members)
}