package omok.controller.event

import omok.domain.board.BoardStatus
import omok.domain.point.Point
import omok.domain.stone.StoneColor

interface GameEventListener {
    fun onCompleteInputPoint(
        stone: StoneColor,
        board: List<List<BoardStatus>>,
    ): Point

    fun onFinishedGame(color: StoneColor)

    fun onFailToAddStone(message: String?)
}
