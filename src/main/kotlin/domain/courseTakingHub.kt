package domain

import commands.CourseTakingApplicationCommand
import commands.CourseTakingApplicationCommandHandler

interface CourseTakingHub {
    fun getApplicationList(user: User): List<Application>?
    fun handle(command: CourseTakingApplicationCommand): CourseTakingApplicationCommand?
}

class CourseTakingApplicationHub(
    val commandHandler: CourseTakingApplicationCommandHandler
):CourseTakingHub {
    override fun getApplicationList(user: User): List<Application>? {
       return null
    }

    override fun handle(command: CourseTakingApplicationCommand): CourseTakingApplicationCommand? {
        return null

    }
}