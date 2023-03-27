package woowacourse.omok.console

import domain.point.Point

interface InputView {
    suspend fun readPosition(): Point
}
