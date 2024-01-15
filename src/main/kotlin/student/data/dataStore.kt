package student.data

import domain.valueObject.Student
import domain.valueObject.StudentId

suspend fun loadStudentById(studentId: StudentId): Student = Student()
