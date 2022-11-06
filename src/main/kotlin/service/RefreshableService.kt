package service

import view.Refreshable

abstract class RefreshableService {

    private val refreshables = mutableListOf<Refreshable>()

    open fun addRefreshable(re: Refreshable) {
        refreshables.add(re)
    }

    fun onAllRefreshables(method: Refreshable.() -> Unit) = refreshables.forEach { it.method()  }

}