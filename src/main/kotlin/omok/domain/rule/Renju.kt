package omok.domain.rule

import omok.domain.board.Point

interface Renju {
    fun is4x4(current: Point): Boolean

    fun is3x3(current: Point): Boolean

    fun is6mok(current: Point): Boolean

    enum class CheckType {
        FOUR_X_FOUR,
        THREE_X_THREE,
        SIX_MOK,
    }
}
