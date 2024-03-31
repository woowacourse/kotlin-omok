package woowacourse.omok.domain.omok.model

data class Place(
    val color: String,
    val rowCoordinate: Int,
    val colCoordinate: Int,
    var id: Long = 0L,
)
