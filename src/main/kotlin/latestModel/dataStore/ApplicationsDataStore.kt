package latestModel.dataStore

import latestModel.dataClass.Application

interface ApplicationsDataStore {
    fun findById(applicationId:String):Application
    fun findByCourseId(courseId:String):List<Application>
    fun findByStudentId(studentId:String):List<Application>

    fun save(application: Application)
    fun save(applications: List<Application>)
    fun delete(applicationId: String)
}