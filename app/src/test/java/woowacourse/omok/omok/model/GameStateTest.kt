package omok.model

import omok.model.domain.gameState.BlackTurn
import omok.model.domain.gameState.GameState
import omok.model.domain.gameState.WhiteTurn
import omok.model.testDouble.BLACK_FORBIDDEN_BOARD_IF_PUT_1_1
import omok.model.testDouble.BLACK_WIN_BOARD_IF_PUT_1_5
import omok.model.testDouble.EMPTY_BOARD
import omok.model.testDouble.POSITION_1_0
import omok.model.testDouble.POSITION_1_1
import omok.model.testDouble.POSITION_1_5
import omok.model.testDouble.WHITE_WIN_BOARD_IF_PUT_1_0
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameStateTest {
    @Test
    fun `흑돌의 턴에 오목을 만들면 흑돌의 승리를 반환한다`() {
        val gameState = GameState(board = BLACK_WIN_BOARD_IF_PUT_1_5, turn = BlackTurn())

        val actual = gameState.play(POSITION_1_5)

        assertThat(actual).isEqualTo(
            GameState(
                board = BLACK_WIN_BOARD_IF_PUT_1_5,
                playing = false,
                turn = BlackTurn(),
            ),
        )
    }

    @Test
    fun `흑돌의 턴에 금수를 두면 흑돌의 턴을 반환한다`() {
        val gameState = GameState(board = BLACK_FORBIDDEN_BOARD_IF_PUT_1_1, turn = BlackTurn())

        val actual = gameState.play(POSITION_1_1)

        assertThat(actual).isEqualTo(
            GameState(
                board = BLACK_FORBIDDEN_BOARD_IF_PUT_1_1,
                playing = true,
                turn = BlackTurn(),
            ),
        )
    }

    @Test
    fun `흑돌의 턴이 끝날 때 오목도 금수도 아닌 경우 백돌의 턴을 반환한다`() {
        val gameState = GameState(board = EMPTY_BOARD)

        val actual = gameState.play(POSITION_1_1)

        assertThat(actual).isEqualTo(
            GameState(
                EMPTY_BOARD,
                playing = true,
                turn = WhiteTurn,
            ),
        )
    }

    @Test
    fun `백돌의 턴에 오목을 만들면 백돌의 승리를 반환한다`() {
        val gameState = GameState(board = WHITE_WIN_BOARD_IF_PUT_1_0, turn = WhiteTurn)

        val actual = gameState.play(POSITION_1_0)

        assertThat(actual).isEqualTo(GameState(board = WHITE_WIN_BOARD_IF_PUT_1_0, playing = false, turn = WhiteTurn))
    }

    @Test
    fun `백돌의 턴이 끝날 때 오목도 장목도 아닌 경우 흑돌의 턴을 반환한다`() {
        val gameState = GameState(board = EMPTY_BOARD, turn = WhiteTurn)

        val actual = gameState.play(POSITION_1_0)

        assertThat(actual).isEqualTo(GameState(board = EMPTY_BOARD, playing = true, turn = BlackTurn()))
    }
}
