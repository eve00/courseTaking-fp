package latestModel.useCase

import latestModel.dataClass.Course
import latestModel.dataClass.RejectedApplication
import latestModel.dataClass.Student
import latestModel.dataStore.ApplicationsDataStore
import latestModel.workflow.createApplication
import latestModel.workflow.createApplicationAsFirstServe

fun applyAsFirstServe(
    student: Student,
    course: Course,
    dataStore: ApplicationsDataStore
) {
    /*IO*/
    val applicationsOfCourse = dataStore.findByCourseId(course.id)
    val applicationsOfStudent = dataStore.findByStudentId(student.id)

    /*workflow*/
    val newApplication = createApplicationAsFirstServe(student,
        course,
        applicationsOfStudent.filterNot { it is RejectedApplication }.size,
        checkCanApplyAsFirstserve = {
            applicationsOfCourse.size <= course.capacity
        }
    ).getOrThrow()

    /*IO*/
    dataStore.save(newApplication)
}