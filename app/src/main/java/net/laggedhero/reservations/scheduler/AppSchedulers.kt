package net.laggedhero.reservations.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulers: Schedulers {
    override fun io(): Scheduler {
        return io.reactivex.schedulers.Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
