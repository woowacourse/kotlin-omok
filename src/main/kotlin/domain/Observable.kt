package domain.domain

import domain.view.Observer

interface Observable {
    fun registerObserver(o: Observer)
    fun removeObserver(o: Observer)
    fun notifyObserver()
}
