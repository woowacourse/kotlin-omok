package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.position.Stone
import omok.domain.model.rule.Rule
import omok.domain.model.stone.StoneType

class Turn(
    override val board: Board,
    private val rule: Rule,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(position: Position): OmokState {
        val stone = Stone(position, stoneType)
        if (rule.canPlace(board.stones, stone).not()) return this
        val addedBoard = board.addedBoard(stone)
        if (rule.checkWin(addedBoard.stones, stone)) return Finish(board, stoneType)
        return Turn(addedBoard, rule, stoneType.reverse())
    }
}
