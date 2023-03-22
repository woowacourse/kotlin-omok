package omok.model.game

import omok.model.external.rule.WhiteRenjuRule
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor

class WhiteOmokRuleAdapter(private val board: Board) : OmokRuleAdapter() {
    private val whiteRenjuRule: WhiteRenjuRule = WhiteRenjuRule(boardWidth = board.sizeX, boardHeight = board.sizeY)

    override fun checkWin(coordinate: Coordinate): PlacementState {
        val isWin = whiteRenjuRule.checkWin(
            targetPoints = board.getStonePoints(GoStoneColor.WHITE),
            otherPoints = board.getStonePoints(GoStoneColor.BLACK),
            startPoint = coordinate.toPoint()
        )

        return if (isWin) PlacementState.WIN else PlacementState.STAY
    }
}
