package view

import dto.VectorSystem

class PlaceStoneObserver {
    private val placeEvent: MutableList<PlaceStoneObservable> = mutableListOf()

    fun subscribe(placeStoneObservable: PlaceStoneObservable) {
        placeEvent.add(placeStoneObservable)
    }

    fun unsubscribe(placeStoneObservable: PlaceStoneObservable) {
        placeEvent.remove(placeStoneObservable)
    }

    fun notify(coordinate: VectorSystem): List<Boolean> {
        return placeEvent.map {
            it.placeStone(coordinate)
        }
    }
}
