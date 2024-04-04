package woowacourse.omok.model.event

import woowacourse.omok.model.omok.Position

fun interface OnPlaceListener {
    fun onPlace(): Position
}
