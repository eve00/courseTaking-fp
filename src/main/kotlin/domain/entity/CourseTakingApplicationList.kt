package domain.entity

data class ApplicationList(
    val id: StudentId = StudentId(String()),
    val applications: List<Application> = emptyList<Application>(),
)

data class UpdatedApplicationList(
    val id: StudentId,
    val applications: List<Application>,
)
