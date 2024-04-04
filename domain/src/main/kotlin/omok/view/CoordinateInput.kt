package omok.view

import omok.model.Coordinate

interface CoordinateInput {
    fun readPosition(): Coordinate
}
