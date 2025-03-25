package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.Point
import omok.domain.stone.StoneColor

class BlackTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.BLACK

    override fun place(point: Point): State {
        checkViolation(point)
        val newBoard = omokBoard.put(stoneColor, point)
        return when {
            newBoard.isOmok(stoneColor, point) -> Finished(newBoard, stoneColor)
            newBoard.isFull() -> Finished(newBoard, null)
            else -> WhiteTurn(newBoard)
        }
    }
}
