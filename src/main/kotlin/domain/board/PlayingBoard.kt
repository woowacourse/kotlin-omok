package domain.board

import domain.OmokResult
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

/**
 * TODO: 보드가 보드판의 개념보다는 배치되어 있는 돌들을 관리하는 보드 같은 느낌.
 *
 * 돌을 관리하는 방법에 대한 관점에서 생각해본다면, 아래와 같이 해석할 수도 있을 것 같아요.
 * 구조적으로 어떤 접근 방식이 좋을지 한번 생각 해보시면 좋을 것 같습니다.
 *
 * 15행 15열의 보드판이 있다.
 * X행 Y열의 블록에는 흰색 돌이 놓여져 있다.
 * X행 Y열의 블록에는 아무런 돌이 놓여져 있지 않다.
 * 배치되어 있는 돌들을 알 수 있다.
 */
class PlayingBoard(placedStones: List<Stone> = listOf()) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val winningColor: Color
        get() {
            throw IllegalStateException(PLAYING_GAME_ERROR)
        }

    override val isFinished: Boolean = false

    /**
     * TODO: 33 44와 같은 규칙이 인터페이스를 통해 판단이 되어야 하지 않을까?
     *
     * 3x3 4x4와 같은 규칙이 위 인터페이스를 통해 같이 판단이 되어야 하지 않을까요??
     * nextBoard()에서 판단하는 것과 isPossiblePut()에서 판단하는 것으로 파편화가 되어 있는 느낌입니다.
     */
    override fun isPossiblePut(position: Position): Boolean =
        !placedStones.any { stone -> stone.position == position }

    override fun putStone(stone: Stone): Board {
        /**
         * TODO: 특정 위치에 돌을 놓을 수 없다는 것이 논리적 오류?
         *
         * 특정 위치에 돌을 놓을 수 없다는 것이 논리적인 오류로 봐야만 할까요?
         * 돌을 놓을 수 없다면, 돌을 놓기 전과 동일한 상태가 유지되어야 하지는 않을까요?
         */
        if (isPossiblePut(stone.position).not()) throw IllegalArgumentException(PLACED_STONE_ERROR)
        return nextBoard(stone)
    }

    private fun nextBoard(newStone: Stone): Board {
        val omokResult = OmokResult.valueOf(getStones(), newStone)
        val nextStones = getStones() + newStone
        return when (omokResult) {
            OmokResult.FIVE_STONE_WINNING -> FinishedBoard(nextStones, newStone.color)
            OmokResult.FORBIDDEN -> FinishedBoard(nextStones, !newStone.color)
            OmokResult.RUNNING -> PlayingBoard(nextStones)
        }
    }

    fun putStone2(getPosition: (latestStone: Stone?) -> Position, turnColor: Color): PlayingBoard? {
        val position = getPosition(getLatestStone())

        if (isPossiblePut(position).not()) {
            return null
        }
        return nextBoard2(Stone(position, turnColor))
    }

    private fun nextBoard2(newStone: Stone): PlayingBoard {
        val omokResult = OmokResult.valueOf(getStones(), newStone)
        val nextStones = getStones() + newStone

        return PlayingBoard(nextStones)
    }

    companion object {
        private const val PLAYING_GAME_ERROR = "[ERROR] 현재 게임이 진행중입니다."
        private const val PLACED_STONE_ERROR = "[ERROR] 이미 놓아진 돌이 있습니다."
    }
}
