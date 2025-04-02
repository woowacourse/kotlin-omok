package woowacourse.omok.data.model

data class OmokGameDto(
    val gameId: Int,
    val lastTurn: String,
    val board: OmokBoardDto,
)
