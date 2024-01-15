package course.data

import valueObject.common.Identifier


typealias CourseId = Identifier<Course, String>


data class Course(
    val id: CourseId = CourseId(String()),
    val name: String = String(),
    val semester: Semester = Semester.FIRST,
    val dowAndPeriod: DowAndPeriod = DowAndPeriod(Dow.MONDAY, Period.FIRST),
    val capacity: Int = 100,
    val credit: Int = 1
)

data class DowAndPeriod(
    val dow: Dow,
    val period: Period
)

enum class Dow(val value: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday")
}

enum class Period(val value: String) {
    FIRST("1st"),
    SECOND("2nd"),
    THIRD("3rd"),
    FOURTH("4th"),
    FIFTH("5th")
}

enum class Semester(val value: String) {
    FIRST("first"), SECOND("second")
}