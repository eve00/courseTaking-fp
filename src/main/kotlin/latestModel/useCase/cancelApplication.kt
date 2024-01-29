package latestModel.useCase

import latestModel.dataClass.NotRegisteredApplication
import latestModel.dataStore.ApplicationsDataStore

fun cancelApplication(
    applicationId: String,
    applicationsDataStore: ApplicationsDataStore
){
    /*IO*/
    val application = applicationsDataStore.findById(applicationId)

    if(application is NotRegisteredApplication){
        /*IO*/
        applicationsDataStore.delete(application.id)
    }
}