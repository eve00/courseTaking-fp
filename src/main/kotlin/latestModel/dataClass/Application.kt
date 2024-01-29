package latestModel.dataClass


sealed class Application
class NotRegisteredApplication(
    val id: String,
    val student: Student,
    val course: Course
):Application()

class RegisteredApplication(
    val id: String,
    val student: Student,
    val course: Course
):Application()

class RejectedApplication(
    val id: String,
    val student: Student,
    val course: Course
):Application()

