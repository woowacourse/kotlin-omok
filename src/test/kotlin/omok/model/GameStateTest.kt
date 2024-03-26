package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameStateTest {
    private lateinit var gameState: GameState

    @BeforeEach
    fun setUp() {
        gameState = GameState.Running.BlackTurn.Start(Board())
    }

    @Test
    fun `BlackTurn에서 돌을 두면 WhiteTurn으로 간다`() {
        assertThat(
            BLACK_STATE.placeStone(
                A1,
            ),
        ).isInstanceOf(GameState.Running.WhiteTurn.Start::class.java)
    }

    @Test
    fun `WhiteTurn에서 돌을 두면 BlackTurn으로 간다`() {
        assertThat(
            WHITE_STATE.placeStone(
                A1,
            ),
        ).isExactlyInstanceOf(GameState.Running.BlackTurn.Start::class.java)
    }

    @Test
    fun `오목이 되면 Finish 상태로 변한다`() {
        OMOK_FINISH.forEach { stone ->
            gameState =
                gameState.placeStone(
                    stone,
                )
        }
        assertThat(gameState).isInstanceOf(GameState.Finish::class.java)
    }
}
