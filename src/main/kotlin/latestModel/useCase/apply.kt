package latestModel.useCase

import latestModel.dataClass.Application
import latestModel.dataClass.Course
import latestModel.dataClass.Student
import latestModel.dataStore.ApplicationsDataStore
import latestModel.workflow.createApplication
import latestModel.workflow.createApplicationWithFirstArrival

val MAX_CREDITS = 24

fun apply(
    student: Student,
    course: Course,
    dataStore: ApplicationsDataStore
) {
    /*IO*/
    val applications = dataStore.findByStudentId(student.id)

    /*workflow*/
    val createdApplication = createApplication(student, course,
        checkTotalCredits = {
            applications.map { it.course.credit }.reduce { acc, credit -> acc + credit } + course.credit <= MAX_CREDITS
        }
    ).getOrThrow()

    dataStore.save(createdApplication)
}

fun applyAsFirstServe(
    student: Student,
    course: Course,
    dataStore: ApplicationsDataStore
) {
    /*IO*/
    val applicationsOfCourse = dataStore.findByCourseId(course.id)
    /*IO*/
    val applicationsOfStudent = dataStore.findByStudentId(student.id)

    /*workflow*/
    val newApplication = createApplicationWithFirstArrival(student, course,
        checkTotalCredits = {
            applicationsOfStudent.map { it.course.credit }.reduce { acc, credit -> acc + credit } + course.credit <= MAX_CREDITS
        },
        checkCanApplyWithFirstArrival = {
            applicationsOfCourse.size <= course.capacity
        }
    ).getOrThrow()

    /*IO*/
    dataStore.save(newApplication)
}
