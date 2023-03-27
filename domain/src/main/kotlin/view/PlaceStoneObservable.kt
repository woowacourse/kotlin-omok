package view

import dto.VectorSystem

interface PlaceStoneObservable {
    fun placeStone(coordinate: VectorSystem): Boolean
}
