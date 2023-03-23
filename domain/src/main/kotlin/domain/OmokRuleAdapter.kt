package domain

import com.example.rule.ArkBoard
import com.example.rule.OmokLine
import com.example.rule.OmokPoint
import com.example.rule.Rule
import com.example.rule.XCoordinate
import com.example.rule.YCoordinate
import com.example.rule.state.BlackStoneState
import com.example.rule.state.EmptyStoneState
import com.example.rule.state.StoneState
import com.example.rule.state.WhiteStoneState

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
        return OmokPoint(
            XCoordinate(stone.column.plus('A'.code).toChar()),
            YCoordinate(15 - stone.row)
        )
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
