package service

import view.Refreshable

/**
 *  Abstrakt Klasse zur Verwaltung verschiedener Refreshables
 */

abstract class RefreshableService {

    //Liste von Refreshables
    private val refreshables = mutableListOf<Refreshable>()

    /**
     * fügt ein [Refreshable] zu [refreshables] , das bei bei verwendung von [onAllRefreshables] aufgerufen wird .
     */
    open fun addRefreshable(re: Refreshable) {
        refreshables.add(re)
    }

    /**
     *
     */
    fun onAllRefreshables(method: Refreshable.() -> Unit) = refreshables.forEach { it.method()  }

}