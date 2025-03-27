package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class BlackTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.BLACK

    override fun place(point: Point): State {
        val newStone = Stone(stoneColor, point)
        val newBoard = omokBoard.place(newStone)
        return when {
            newBoard.isOmok(newStone) -> Finished(newBoard, stoneColor)
            newBoard.isFull() -> Finished(newBoard, null)
            else -> WhiteTurn(newBoard)
        }
    }
}
