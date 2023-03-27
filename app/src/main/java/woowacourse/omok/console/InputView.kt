package woowacourse.omok.console

import domain.point.Point

interface InputView {
//    suspend fun readPosition(onPutStone: (Point) -> Unit)
    suspend fun readPosition(): Point
}
