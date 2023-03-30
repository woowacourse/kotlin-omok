package domain.state

import domain.Board
import domain.Team
import domain.rule.RenjuRule
import domain.stone.Stone
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

internal class FinishedTest {

    @Test
    fun `오목이 완성되지 않은 오목판으로 게임 종료 상태를 생성하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy { Finished(Team.BLACK, Board(RenjuRule)) }
            .withMessage("오목이 완성되지 않았다면 게임 종료 상태가 될 수 없습니다.")
    }

    @Test
    fun `게임이 종료 상태일 때 돌을 두면 에러가 발생한다`() {
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

        assertThatIllegalStateException().isThrownBy { gameState.placeStoneOnBoard(Stone('B', 5)) }
            .withMessage("게임이 끝났다면 돌을 둘 수 없습니다.")
    }

    @Test
    fun `게임이 종료 상태일 때 마지막으로 둔 돌의 좌표를 요청하면 현재 턴의 팀이 마지막으로 둔 돌의 좌표를 반환한다`() {
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
        gameState = gameState.placeStoneOnBoard(Stone('A', 5))

        val lastPoint = gameState.getLastPoint()

        Assertions.assertThat(lastPoint).isEqualTo(board.getLastPoint(gameState.turn))
    }
}