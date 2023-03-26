package view

import domain.point.Point

interface InputView {
    suspend fun askPosition(onPutStone: (Point) -> Unit)
}
