package omok.model.event

import omok.model.Position

fun interface OnPlaceListener {
    fun onPlace(): Position
}
