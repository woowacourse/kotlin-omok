package woowacourse.omok.view

import domain.point.Point

interface InputView {
    suspend fun readPosition(onPutStone: (Point) -> Unit)
}
