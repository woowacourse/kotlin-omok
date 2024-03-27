package omok.model.game

import omok.model.OmokStone
import omok.model.board.Board

data class OmokGameResult(val board: Board, val winner: OmokStone)
