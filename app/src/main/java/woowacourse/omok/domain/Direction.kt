package woowacourse.omok.domain

data class Direction(
    val rowDelta: Int,
    val colDelta: Int,
) {
    operator fun unaryMinus() = Direction(-rowDelta, -colDelta)
}
