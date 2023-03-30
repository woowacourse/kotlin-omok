package domain.state

import domain.Board
import domain.stone.Point
import domain.stone.Stone
import domain.Team

class Finished(override val turn: Team, override val board: Board) : GameState() {

    init {
        require(board.hasTeamThatCompletedOmok()) { NOT_COMPLETED_OMOK_ERROR }
    }

    override fun placeStoneOnBoard(stone: Stone): GameState {
        throw IllegalStateException("게임이 끝났다면 돌을 둘 수 없습니다.")
    }

    override fun canPlace(stone: Stone): Boolean = false

    override fun getLastPoint(): Point? = board.getLastPoint(turn)

    companion object {
        private const val NOT_COMPLETED_OMOK_ERROR = "오목이 완성되지 않았다면 게임 종료 상태가 될 수 없습니다."
    }
}