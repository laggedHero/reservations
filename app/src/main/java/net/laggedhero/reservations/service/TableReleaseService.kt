package net.laggedhero.reservations.service

import android.app.IntentService
import android.content.Intent
import net.laggedhero.reservations.usecase.TableCleanUp
import org.koin.android.ext.android.inject

class TableReleaseService : IntentService("TableReleaseService") {

    val tableCleanUp: TableCleanUp by inject()

    override fun onHandleIntent(intent: Intent?) {
        tableCleanUp.cleanUp()
            .subscribe(
                {},
                { _ -> }
            )
    }
}
