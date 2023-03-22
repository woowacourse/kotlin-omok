package view

import dto.VectorDTO

class PlaceStoneObserver {
    private val placeEvent: MutableList<PlaceStoneObservable> = mutableListOf()

    fun subscribe(placeStoneObservable: PlaceStoneObservable) {
        placeEvent.add(placeStoneObservable)
    }

    fun unsubscribe(placeStoneObservable: PlaceStoneObservable) {
        placeEvent.remove(placeStoneObservable)
    }

    fun notify(coordinate: VectorDTO): List<Boolean> {
        return placeEvent.map {
            it.placeStone(coordinate)
        }
    }
}
