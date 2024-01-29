package latestModel.dataStore

import latestModel.dataClass.Application
import latestModel.dataClass.NotRegisteredApplication

interface ApplicationsDataStore {
    fun findById(applicationId:String):Application
    fun findByCourseId(courseId:String):List<NotRegisteredApplication>
    fun findByStudentId(studentId:String):List<Application>

    fun save(application: Application)
    fun save(applications: List<Application>)
    fun delete(applicationId: String)
}