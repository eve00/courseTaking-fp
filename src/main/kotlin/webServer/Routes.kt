package webServer

import commands.CancelCourseTakingApplication
import commands.CreateCourseTakingApplication
import domain.Application
import domain.CourseTakingHub
import domain.User
import kotlinx.serialization.json.Json
import org.http4k.core.*
import org.http4k.core.body.form
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

/*
* TODO:
* 抽選、先着管理、登録、科目取得
* */
class CourseTaking(val hub: CourseTakingHub): HttpHandler {
    override fun invoke(request: Request): Response = httpHandler(request)

    val httpHandler = routes(
        "/ping" bind Method.GET to { Response(Status.OK) },
        "/application/{user}" bind Method.GET to ::getApplicationList,
        "/application/{user}" bind Method.POST to ::applyCourseTaking,
        "/application/{user}" bind Method.DELETE to ::cancelCourseTaking
    )

    //Request -> User -> Result -> Response
    private fun getApplicationList(request: Request): Response {
        return  request.extractUser()
            ?.let { hub.getApplicationList(it) }
            ?.let(::convertApplicationListToJson) //TODO("convert to Json")
            ?.let(::toResponse)
            ?: Response(Status.NOT_FOUND)
    }

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

    data class JsonData(val raw:String)
    fun convertApplicationListToJson(list: List<Application>): JsonData {
        /*TODO: serialize list*/
        return JsonData("nothing")
    }

    fun toResponse(data: JsonData): Response =
        Response(Status.OK).body(data.raw)



    private fun Request.extractUser(): User = path("user").orEmpty().let(::User)
    private fun Request.extractApplication(): Application? {
        val id = form("applicationId") ?:return null
        val course = form("course") ?: return null
        return Application(id, course)
    }
}

