package webServer

import commands.CancelCourseTakingApplication
import commands.CreateCourseTakingApplication
import domain.Application
import domain.CourseTakingHub
import domain.User
import org.http4k.core.*
import org.http4k.core.body.form
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes


class CourseTaking(val hub: CourseTakingHub): HttpHandler {
    override fun invoke(request: Request): Response = httpHandler(request)

    val httpHandler = routes(
        "/ping" bind Method.GET to { Response(Status.OK) },
        "/application/{user}" bind Method.POST to ::applyCourseTaking,
        "/application/{user}" bind Method.DELETE to ::cancelCourseTaking
    )

    //Request -> User,Application -> Result -> Response
    private fun applyCourseTaking(request: Request): Response {
        val user = request.extractUser()
        return  request.extractApplication()
            ?.let { CreateCourseTakingApplication(user, it) }
            ?.let(hub::handle)//Result噛ませたい
            ?.let { Response(Status.OK) }
            ?: Response(Status.BAD_REQUEST)
    }

    //Request -> User,Application -> Result -> Response
    private fun cancelCourseTaking(request: Request): Response {
        val user = request.extractUser()
        return  request.extractApplication()
            ?.let { CancelCourseTakingApplication(user, it) }
            ?.let(hub::handle)//Result噛ませたい
            ?.let { Response(Status.OK) }
            ?: Response(Status.BAD_REQUEST)
    }


    private fun Request.extractUser(): User = path("user").orEmpty().let(::User)
    private fun Request.extractApplication(): Application? {
        val id = form("applicationId") ?:return null
        val course = form("course") ?: return null
        return Application(id, course)
    }
}