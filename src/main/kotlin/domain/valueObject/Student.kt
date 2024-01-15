package domain.valueObject

import domain.valueObject.common.Faculty
import domain.valueObject.common.Identifier

typealias StudentId = Identifier<Student, String>



data class Student(
    val id: StudentId = StudentId(String()),
    val name: String = String(),
    val grade: Int = 0,
    val faculty: Faculty = Faculty.ECONOMICS,
    val maxCredit: Int = 26
) {

    fun getId(): StudentId {
        return id
    }
    fun getGrade():Int{
        return grade
    }
    fun getFaculty(): Faculty {
        return faculty
    }
}