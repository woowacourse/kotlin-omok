import rule.OmokRule
import rule.type.Violation

class GameBoard {
    private var lastStone: Stone? = null

    private val _blackStones = mutableListOf<Stone>()
    val blackStones get() = _blackStones.toList()
    private val _whiteStones = mutableListOf<Stone>()
    val whiteStones get() = _whiteStones.toList()

    fun putStone(
        stoneColor: StoneColor,
        rule: OmokRule,
        onPositionReceived: (Stone?) -> String,
    ): Boolean {
        val position = onPositionReceived(lastStone)
        val stone = Stone.of(position, stoneColor)

        if (isExistPosition(stone)) {
            println("중복 위치 알려주는 기능 추가 필요")
            return false
        }

        val violateType = rule.checkAnyFoulCondition(blackStones, whiteStones, stone.position)
        val canPut =
            when (violateType) {
                Violation.DOUBLE_THREE -> false // 3 - 3
                Violation.DOUBLE_FOUR -> false // 4 - 4
                Violation.OVERLINE -> false // 선 밖
                Violation.NONE -> true // 가능한 위치
            }

        // 놓을 수 있는 위치라면
        if (canPut) {
            when (stoneColor) {
                StoneColor.BLACK -> _blackStones.add(stone)
                StoneColor.WHITE -> _whiteStones.add(stone)
            }
            lastStone = stone
        }

        return true
    }

    private fun isExistPosition(stone: Stone): Boolean =
        blackStones.any { existedStone -> existedStone.isSamePosition(stone) } ||
            whiteStones.any { existedStone ->
                existedStone.isSamePosition(stone)
            }

    fun judge(rule: OmokRule): Boolean {
        return rule.checkWin(
            blackStones,
            whiteStones,
            lastStone!!.position,
        )
    }
}
