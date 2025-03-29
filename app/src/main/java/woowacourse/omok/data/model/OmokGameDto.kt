package woowacourse.omok.data.model

data class OmokGameDto(
    val lastTurn: String,
    val board: Map<Pair<Int, Int>, String>,
)
