package domain.entity

import domain.entity.common.Faculty
import domain.entity.common.Identifier

typealias StudentId = Identifier<Student, String>



data class Student(
    private val id: StudentId,
    private val name: String,
    private val grade: Int,
    private val faculty: Faculty,
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