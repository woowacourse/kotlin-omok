package omok.domain.state

import omok.domain.Point
import omok.domain.Stone

interface State {
    val stones: List<Stone>

    fun place(point: Point): State
}
