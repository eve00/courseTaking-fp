package domain.courseTaking.entity

import domain.courseTaking.entity.CourseTakingSchedule.OfVacant
import domain.courseTaking.entity.CourseTakingSchedule.OfFull
import domain.courseTaking.entity.CourseTakingSchedule.OfInvalid
import domain.courseTaking.entity.Application.OfInvalidated
import domain.valueObject.Student

/*
* factory
* */
fun List<Application>.toCourseTakingSchedule(student: Student): CourseTakingSchedule {
    /*InvalidateされたApplicationは省く*/
    val applicationMap = this.associateBy({ it.course.dowAndPeriod }, { it }).filterNot { it.value is OfInvalidated }
    val totalCredit = this.filterNot { it is OfInvalidated }.map { it.course.credit }.reduce { acc, credit -> acc + credit }
    return when {
        totalCredit < student.maxCredit -> OfVacant(VacantCourseTakingSchedule(student, applicationMap))
        totalCredit == student.maxCredit -> OfFull(FullCourseTakingSchedule(student, applicationMap))
        totalCredit > student.maxCredit -> OfInvalid(InvalidCourseTakingSchedule(student, applicationMap))
        else -> {
            OfInvalid(InvalidCourseTakingSchedule(Student(), mapOf()))
        }
    }
}