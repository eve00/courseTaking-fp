package valueObject

typealias StudentId = Identifier<Student, String>

data class Student(
    val id: StudentId = StudentId(String()),
    val name: String = String(),
    val grade: Int = 0,
    val faculty: Faculty = Faculty.ECONOMICS,
    val maxCredit: Int = 26
)
enum class Faculty {
    ENGINEERING, ECONOMICS
}