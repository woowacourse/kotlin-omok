package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class WhiteTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.WHITE

    override fun place(point: Point): State {
        val newStone = Stone(stoneColor, point)
        omokBoard.checkViolation(newStone)
        val newBoard = omokBoard.place(newStone)
        return when {
            newBoard.isOmok(newStone) -> Finished(newBoard, stoneColor)
            newBoard.isFull() -> Finished(newBoard, null)
            else -> BlackTurn(newBoard)
        }
    }
}
