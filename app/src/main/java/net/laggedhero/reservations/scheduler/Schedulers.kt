package net.laggedhero.reservations.scheduler

import io.reactivex.Scheduler

interface Schedulers {
    fun io(): Scheduler
    fun ui(): Scheduler
}
