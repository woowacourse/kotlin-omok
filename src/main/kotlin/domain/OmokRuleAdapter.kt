package domain

import domain.rule.ArkBoard
import domain.rule.OmokLine
import domain.rule.OmokPoint
import domain.rule.Rule
import domain.rule.XCoordinate
import domain.rule.YCoordinate
import domain.rule.state.BlackStoneState
import domain.rule.state.EmptyStoneState
import domain.rule.state.StoneState
import domain.rule.state.WhiteStoneState

class OmokRuleAdapter : OmokRule {

    // 3*3 or 4*4 check
    override fun isMovable(myboard: OmokBoard, stone: Stone): Boolean {
        val point = convertStoneToPoint(stone)
        val convertedBoard = boardConverter(myboard)
        return Rule(convertedBoard).countOpenThrees(point) < COUNT_CONDITION &&
            Rule(convertedBoard).countOpenFours(point) < COUNT_CONDITION
    }

    // Stone -> OmokPoint
    private fun convertStoneToPoint(stone: Stone): OmokPoint {
        val stoneString = stone.toString()
        return OmokPoint(XCoordinate(stoneString[0]), YCoordinate(stoneString.substring(1).toInt()))
    }

    // OurBoard -> ArkBoard
    private fun boardConverter(myOmokBoard: OmokBoard): ArkBoard {
        return ArkBoard(
            myOmokBoard.value.reversed().mapIndexed { y, row ->
                yCoordinateOmokLinePair(y, row)
            }.toMap()
        )
    }

    private fun yCoordinateOmokLinePair(y: Int, row: List<State>): Pair<YCoordinate, OmokLine> =
        YCoordinate(y + 1) to OmokLine(
            row.mapIndexed { x, state ->
                xCoordinateStoneStatePair(x, state)
            }.toMap()
        )

    private fun xCoordinateStoneStatePair(x: Int, state: State): Pair<XCoordinate, StoneState> =
        XCoordinate(x.plus('A'.code).toChar()) to when (state) {
            State.BLACK -> BlackStoneState
            State.WHITE -> WhiteStoneState
            State.EMPTY -> EmptyStoneState
        }

    companion object {
        private const val COUNT_CONDITION = 2
    }
}
