package latestModel.useCase

import latestModel.dataClass.Course
import latestModel.dataClass.NotRegisteredApplication
import latestModel.dataClass.RejectedApplication
import latestModel.dataClass.Student
import latestModel.dataStore.ApplicationsDataStore
import latestModel.workflow.createApplication

fun apply(
    student:Student,
    course: Course,
    dataStore: ApplicationsDataStore
){
    /*IO*/
    val applications = dataStore.findByStudentId(student.id)

    /*workflow*/
    val newApplication = createApplication(student, course,applications.filterNot { it is RejectedApplication }.size).getOrThrow()

    /*IO*/
    dataStore.save(newApplication)
}

