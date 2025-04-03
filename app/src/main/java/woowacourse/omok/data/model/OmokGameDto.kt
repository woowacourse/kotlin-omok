package woowacourse.omok.data.model

data class OmokGameDto(
    val gameId: Int,
    val host: String,
    val lastTurn: String,
    val board: OmokBoardDto,
)
