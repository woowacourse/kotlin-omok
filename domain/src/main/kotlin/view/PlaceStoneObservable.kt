package view

import dto.VectorDTO

interface PlaceStoneObservable {
    fun placeStone(coordinate: VectorDTO): Boolean
}
