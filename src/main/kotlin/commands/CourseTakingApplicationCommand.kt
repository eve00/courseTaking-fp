package commands

import domain.Application
import domain.User
import events.ApplicationId
import fp.EntityId


sealed class CourseTakingApplicationCommand

data class CreateCourseTakingApplication(val user: User, val application: Application) : CourseTakingApplicationCommand() {
    val id: ApplicationId = EntityId.mint()
}


data class CancelCourseTakingApplication(val user: User, val application: Application) : CourseTakingApplicationCommand() {
    val id: ApplicationId = EntityId.mint()
}