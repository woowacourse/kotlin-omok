package woowacourse.omok.dto

import omok.domain.board.OmokBoard

data class OmokGameDto(
    val nickname: String,
    val board: OmokBoard,
)
