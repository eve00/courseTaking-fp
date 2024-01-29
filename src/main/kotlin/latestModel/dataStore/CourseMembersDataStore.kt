package latestModel.dataStore

import latestModel.dataClass.Student

interface CourseMembersDataStore {

    fun save(members:List<Student>)
}