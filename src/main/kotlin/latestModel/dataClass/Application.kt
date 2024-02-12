package latestModel.dataClass


sealed class Application {
    data class OfCreated(val value: CreatedApplication) : Application()

    data class OfRegistered(val value: RegisteredApplication) : Application()
    data class OfRejected(val value: RejectedApplication) : Application()

    val id: String
        get() = when (this@Application) {
            is OfCreated -> this@Application.id
            is OfRegistered -> this@Application.id
            is OfRejected -> this@Application.id
        }
    val student: Student
        get() = when (this@Application) {
            is OfCreated -> this@Application.student
            is OfRegistered -> this@Application.student
            is OfRejected -> this@Application.student
        }
    val course: Course
        get() = when (this@Application) {
            is OfCreated -> this@Application.course
            is OfRegistered -> this@Application.course
            is OfRejected -> this@Application.course
        }
}

data class CreatedApplication(
    val id: String,
    val student: Student,
    val course: Course
)

data class RegisteredApplication(
    val id: String,
    val student: Student,
    val course: Course
)

data class RejectedApplication(
    val id: String,
    val student: Student,
    val course: Course
)
