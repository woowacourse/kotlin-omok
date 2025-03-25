package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.stone.StoneColor

class WhiteTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.WHITE

    override fun place(point: Point): State {
        val newBoard = omokBoard.put(stoneColor, point)
        return when {
            newBoard.isOmok(stoneColor, point) -> Finished(newBoard, stoneColor)
            newBoard.isFull() -> Finished(newBoard, null)
            else -> BlackTurn(newBoard)
        }
    }
}
