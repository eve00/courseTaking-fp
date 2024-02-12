package latestModel.useCase

import latestModel.dataClass.Application
import latestModel.dataStore.ApplicationsDataStore
import latestModel.workflow.cancelApplication
fun cancelApplication(
    applicationId: String,
    applicationsDataStore: ApplicationsDataStore
) {
    /*IO*/
    val application = applicationsDataStore.findById(applicationId)

    val cancelableApplication = cancelApplication(application,
        checkIsCancelable = {
            application is Application.OfCreated
        })

    /*IO*/
    applicationsDataStore.delete(application.id)

}