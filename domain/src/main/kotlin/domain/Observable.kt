package domain.domain

import domain.view.Observer

interface Observable {
    fun registerObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObserver()
}
