package webServer

import courseManagement.domain.drawAndRegister
import domain.courseTaking.*
import domain.courseManagement.register
import domain.courseTaking.entity.ApplicationId
import domain.valueObject.CourseId
import domain.valueObject.StudentId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

/*
* TODO:
* 抽選、先着管理、登録、科目取得
* */
class CourseTaking() : HttpHandler {
    override fun invoke(request: Request): Response = httpHandler(request)

    val httpHandler = routes(
        /*申請履歴*/
        "/application/{studentId}" bind Method.GET to ::getApplications,
        "/course" bind Method.GET to ::getCourses,
        /*先着申請可能な科目*/
        "/course" bind Method.GET to ::getCoursesCanTake,
        /*申請*/
        "｛format｝/application/{studentId}/{courseId}" bind Method.POST to ::applyCourseTaking,
        "/application/{studentId}/{courseTakingApplicationId}" bind Method.DELETE to ::cancelCourseTaking,
        /*抽選・登録*/
        "/course/{courseId}" bind Method.POST to ::drawAndRegisterCourseMembers,
        "/course/{courseId}" bind Method.POST to ::registerCourseMembers,
    )
    /*
    * {studentId, course}
    * */
    private fun applyCourseTaking(request: Request): Response {
        /*studentId, courseを取得*/
        val studentId = request.extractStudentId() ?: return Response(Status.BAD_REQUEST)
        val courseId = request.extractCourseId() ?: return Response(Status.BAD_REQUEST)

        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                createApplication(studentId,courseId)
            }
        }

        /*responseを返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.BAD_REQUEST)
        }
    }

    /*
    * {applicationId}
    * */
    private fun cancelCourseTaking(request: Request): Response {
        /*requestからapplicationIdを取得*/
        val applicationId = request.extractApplicationId() ?: return Response(Status.BAD_REQUEST)

        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                deleteMyApplication(applicationId)
            }
        }

        /*responseを返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.BAD_REQUEST)
        }
    }

    private fun drawAndRegisterCourseMembers(request: Request): Response {
        /*requestからcourseIdを取得*/
        val courseId = request.extractCourseId() ?: return Response(Status.BAD_REQUEST)

        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                drawAndRegister(courseId)
            }
        }

        /*結果を返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.BAD_REQUEST)
        }
    }

    private fun registerCourseMembers(request: Request): Response {
        /*requestからcourseIdを取得*/
        val courseId = request.extractCourseId() ?: return Response(Status.BAD_REQUEST)

        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                register(courseId)
            }
        }

        /*結果を返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.BAD_REQUEST)
        }
    }


    private fun getCourses(request: Request): Response {
        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                domain.courseManagement.getCourses()
            }
        }

        /*responseを返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.NOT_FOUND)
        }
    }

    private fun getCoursesCanTake(request: Request): Response {
        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                domain.courseManagement.getCoursesCanTake()
            }
        }

        /*responseを返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.NOT_FOUND)
        }
    }

    /*
* {studentId}
* */
    private fun getApplications(request: Request): Response {
        /*requestからstudentIdを取得*/
        val studentId = request.extractStudentId() ?: return Response(Status.BAD_REQUEST)

        val result = CoroutineScope(Dispatchers.IO).async {
            runCatching {
                getMyApplications(studentId)
            }
        }

        /*responseを返す*/
        return if (result.getCompleted().isSuccess) {
            Response(Status.OK)
        } else {
            Response(Status.NOT_FOUND)
        }
    }


    fun Request.extractApplicationId(): ApplicationId? = ApplicationId(String())
    fun Request.extractStudentId(): StudentId? = StudentId(String())
    fun Request.extractCourseId(): CourseId? = CourseId(String())


}

