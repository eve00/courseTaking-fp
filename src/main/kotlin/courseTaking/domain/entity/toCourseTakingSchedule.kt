package courseTaking.domain.entity

import valueObject.Student


/*
* factory
* */
fun List<Application>.toCourseTakingSchedule(student: Student): CourseTakingSchedule {
    /*InvalidateされたApplicationは省く*/
    val applicationMap = this.associateBy({ it.course.dowAndPeriod }, { it }).filterNot { it.value is Application.OfInvalidated }
    val totalCredit = this.filterNot { it is Application.OfInvalidated }.map { it.course.credit }.reduce { acc, credit -> acc + credit }
    return when {
        totalCredit < student.maxCredit -> CourseTakingSchedule.OfVacant(
            VacantCourseTakingSchedule(
                student,
                applicationMap
            )
        )
        totalCredit == student.maxCredit -> CourseTakingSchedule.OfFull(
            FullCourseTakingSchedule(
                student,
                applicationMap
            )
        )
        totalCredit > student.maxCredit -> CourseTakingSchedule.OfInvalid(
            InvalidCourseTakingSchedule(
                student,
                applicationMap
            )
        )
        else -> {
            CourseTakingSchedule.OfInvalid(InvalidCourseTakingSchedule(Student(), mapOf()))
        }
    }
}