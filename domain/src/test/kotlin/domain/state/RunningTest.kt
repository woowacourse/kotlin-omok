package domain.state

import domain.Board
import domain.Team
import domain.rule.RenjuRule
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RunningTest {

    @Test
    fun `게임 진행 상태일 때 돌을 뒀을 때 오목이 완성되지 않았다면 다음 턴의 게임 진행 상태를 반환한다`() {
        val turn = Team.BLACK
        val gameState: GameState = Running(turn, Board(RenjuRule))

        val nextState = gameState.placeStoneOnBoard(Stone('A', 1))

        assertAll(
            { assertThat(nextState.turn).isEqualTo(turn.next()) },
            { assertThat(nextState).isInstanceOf(Running::class.java) }
        )
    }

    @Test
    fun `게임 진행 상태일 때 돌을 뒀을 때 오목이 완성되었다면 게임 종료 상태를 반환한다`() {
        var gameState: GameState = Running(Team.BLACK, Board(RenjuRule))
        gameState = gameState.placeStoneOnBoard(Stone('A', 1))
        gameState = gameState.placeStoneOnBoard(Stone('B', 1))
        gameState = gameState.placeStoneOnBoard(Stone('A', 2))
        gameState = gameState.placeStoneOnBoard(Stone('B', 2))
        gameState = gameState.placeStoneOnBoard(Stone('A', 3))
        gameState = gameState.placeStoneOnBoard(Stone('B', 3))
        gameState = gameState.placeStoneOnBoard(Stone('A', 4))
        gameState = gameState.placeStoneOnBoard(Stone('B', 4))

        gameState = gameState.placeStoneOnBoard(Stone('A', 5))

        assertThat(gameState).isInstanceOf(Finished::class.java)
    }

    @Test
    fun `게임 진행 상태일 때 마지막으로 둔 돌의 좌표를 요청하면 이전 턴의 팀이 마지막으로 둔 돌의 좌표를 반환한다`() {
        val board = Board(RenjuRule)
        var gameState: GameState = Running(Team.BLACK, board)
        gameState = gameState.placeStoneOnBoard(Stone('A', 1))
        gameState = gameState.placeStoneOnBoard(Stone('B', 1))
        gameState = gameState.placeStoneOnBoard(Stone('A', 2))
        gameState = gameState.placeStoneOnBoard(Stone('B', 2))
        gameState = gameState.placeStoneOnBoard(Stone('A', 3))
        gameState = gameState.placeStoneOnBoard(Stone('B', 3))
        gameState = gameState.placeStoneOnBoard(Stone('A', 4))
        gameState = gameState.placeStoneOnBoard(Stone('B', 4))

        val lastPoint = gameState.getLastPoint()

        assertThat(lastPoint).isEqualTo(board.getLastPoint(gameState.turn.previous()))
    }
}