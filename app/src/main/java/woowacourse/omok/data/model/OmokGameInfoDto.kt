package woowacourse.omok.data.model

data class OmokGameInfoDto(
    val lastTurn: String,
    val board: Map<Pair<Int, Int>, String>,
)
