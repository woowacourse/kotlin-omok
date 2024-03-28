package woowacourse.omok.model.event

import woowacourse.omok.model.Position

fun interface OnPlaceListener {
    fun onPlace(): Position
}
