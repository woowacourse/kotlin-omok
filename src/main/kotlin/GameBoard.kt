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
            when (stoneColor) {
                StoneColor.BLACK -> _blackStones.add(stone)
                StoneColor.WHITE -> _whiteStones.add(stone)
            }
            lastStone = stone
            return GameState.Success
        }

        return GameState.Fail(violateType)
    }

    fun judge(rule: OmokRule): Boolean =
        rule.checkWin(
            blackStones,
            whiteStones,
            lastStone!!.position,
        )
}
