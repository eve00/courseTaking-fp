package latestModel.useCase

import latestModel.dataClass.Application
import latestModel.dataClass.Course
import latestModel.dataStore.ApplicationsDataStore
import latestModel.dataStore.CourseMembersDataStore
import latestModel.workflow.registerApplications
import latestModel.workflow.selectApplications

fun selectAndRegister(
    courseId: String,
    applicationsDataStore: ApplicationsDataStore,
    courseMembersDataStore: CourseMembersDataStore

) {
    /*IO*/
    val applications = applicationsDataStore.findByCourseId(courseId)
    val course = Course(courseId)

    /*workflow*/
    val applicationsHasRejected =
        selectApplications(applications.filterIsInstance<Application.OfCreated>(), course.capacity)
    val registeredApplication = registerApplications(applications.filterIsInstance<Application.OfCreated>())

    /*IO*/
    val rejectedApplication = applicationsHasRejected.filterIsInstance<Application.OfRejected>()
    applicationsDataStore.save(registeredApplication + rejectedApplication)
    val members = registeredApplication.map { it.student }
    courseMembersDataStore.save(members)
}