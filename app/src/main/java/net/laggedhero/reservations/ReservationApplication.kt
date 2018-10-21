package net.laggedhero.reservations

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import net.laggedhero.reservations.injection.insertKoin
import net.laggedhero.reservations.service.TableReleaseService

class ReservationApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        insertKoin()
        startCleanUpTimer()
    }

    private fun startCleanUpTimer() {
        val intent = Intent(this, TableReleaseService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }
}
