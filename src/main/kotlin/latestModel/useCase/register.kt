package latestModel.useCase

import latestModel.dataClass.Application
import latestModel.dataStore.ApplicationsDataStore
import latestModel.dataStore.CourseMembersDataStore
import latestModel.workflow.registerApplications


fun register(
    courseId: String,
    applicationsDataStore: ApplicationsDataStore,
    courseMembersDataStore: CourseMembersDataStore
){
    /*IO*/
    val applications = applicationsDataStore.findByCourseId(courseId)

    /*workflow*/
    val registeredApplication = registerApplications(applications.filterIsInstance<Application.OfCreated>())

    /*IO*/
    applicationsDataStore.save(registeredApplication)
    val members = registeredApplication.map { it.student }
    courseMembersDataStore.save(members)
}