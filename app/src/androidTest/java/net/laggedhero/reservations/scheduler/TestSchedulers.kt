package net.laggedhero.reservations.scheduler

class TestSchedulers : Schedulers {
    override fun io() = io.reactivex.schedulers.Schedulers.trampoline()

    override fun ui() = io.reactivex.schedulers.Schedulers.trampoline()
}
