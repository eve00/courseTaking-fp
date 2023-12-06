package webServer

import commands.CourseTakingApplicationCommandHandler
import domain.CourseTakingApplicationHub
import domain.CourseTakingHub
import jdk.internal.org.jline.utils.Colors.s
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.util.*

fun main(args: Array<String>) {

    val commandHandler = CourseTakingApplicationCommandHandler()
    val hub = CourseTakingApplicationHub( commandHandler)

    CourseTaking(hub).asServer(Jetty(8080)).start()

    println("Server started at http://localhost:8080")

}

/*TODO: 1. Simple Type*/
/*値オブジェクトに仕様を語らせる*/

/*TODO: 2. implement steps*/


/*TODO: 3. create Events*/

/*TODO: 4. composing the pipeline*/

/*TODO: 5. injecting dependencies*/

/*
API: ApplyCourseTakingApi

Request ->  UnValidatedCourseTaking -> ValidatedCourseTaking -> Result<Event> -> Response

validateCourseTaking... その学生の情報から申請が有効なのかを検証する

Event：CourseTakingApplied 履修登録が申請された


*/









