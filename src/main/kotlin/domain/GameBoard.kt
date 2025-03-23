package domain

import domain.position.Position
import domain.stone.Stone
import domain.stone.StoneColor
import result.GameState
import rule.OmokRule

class GameBoard {
    private var lastStone: Stone? = null
    private val _blackStones = mutableListOf<Stone>()
    val blackStones get() = _blackStones.toList()
    private val _whiteStones = mutableListOf<Stone>()
    val whiteStones get() = _whiteStones.toList()

    fun putStone(
        stoneColor: StoneColor,
        rule: OmokRule,
        onPositionReceived: (Stone?) -> Position,
    ): GameState {
        val position = onPositionReceived(lastStone)
        val stone = Stone.of(position, stoneColor)

        val violateType = rule.checkAnyFoulCondition(blackStones, whiteStones, stone.position)
        if (violateType.isNone()) {
            successStateProcess(stoneColor, stone)
            return GameState.Success
        }
        return GameState.Fail(violateType)
    }

    private fun successStateProcess(
        stoneColor: StoneColor,
        stone: Stone,
    ) {
        when (stoneColor) {
            StoneColor.BLACK -> _blackStones.add(stone)
            StoneColor.WHITE -> _whiteStones.add(stone)
        }
        lastStone = stone
    }

    fun judge(rule: OmokRule): Boolean =
        lastStone?.let {
            rule.checkWin(
                blackStones,
                whiteStones,
                lastStone!!.position,
            )
        } ?: false
}
