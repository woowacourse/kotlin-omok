package domain

import domain.rule.ArkBoard
import domain.rule.OmokLine
import domain.rule.OmokPoint
import domain.rule.Rule
import domain.rule.XCoordinate
import domain.rule.YCoordinate
import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.WhiteStoneState

class OmokRuleAdapter : OmokRule {
    // 3*3 or 4*4 check
    override fun isForbidden(myBoard: OmokBoard, stone: Stone): Boolean {
        val point = convertStoneToPoint(stone)
        val convertedBoard = boardConverter(myBoard)
        return Rule(convertedBoard).countOpenThrees(point) <= 1 && Rule(convertedBoard).countOpenFours(point) <= 1
    }

    // Stone -> OmokPoint
    private fun convertStoneToPoint(stone: Stone): OmokPoint {
        val stoneString = stone.toString()
        return OmokPoint(XCoordinate(stoneString[0]), YCoordinate(stoneString.substring(1).toInt()))
    }

    // OurBoard -> ArkBoard
    private fun boardConverter(myBoard: OmokBoard): ArkBoard {
        return ArkBoard(
            myBoard.value.reversed().mapIndexed { y, row ->
                YCoordinate(y + 1) to OmokLine(
                    row.mapIndexed { x, state ->
                        XCoordinate(x.plus('A'.code).toChar()) to when (state) {
                            State.BLACK -> BlackStoneState
                            State.WHITE -> WhiteStoneState
                            State.EMPTY -> EmptyStoneState
                        }
                    }.toMap()
                )
            }.toMap()
        )
    }
}
