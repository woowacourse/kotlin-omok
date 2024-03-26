package omok.model

import omok.library.BlackStoneOmokRule
import omok.library.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameStateTest {
    private lateinit var board: Board
    private lateinit var omokRule: OmokRule
    private lateinit var gameState: GameState

    @BeforeEach
    fun setUp() {
        board = Board()
        omokRule = BlackStoneOmokRule
        gameState = GameState.Running.BlackTurn(Board())
    }

    @Test
    fun `BlackTurn에서 돌을 두면 WhiteTurn으로 간다`() {
        assertThat(
            BLACK_STATE.updateState(
                { },
                { },
                { A1 },
            ),
        ).isInstanceOf(GameState.Running.WhiteTurn::class.java)
    }

    @Test
    fun `WhiteTurn에서 돌을 두면 BlackTurn으로 간다`() {
        assertThat(
            WHITE_STATE.updateState(
                { },
                { },
                { A1 },
            ),
        ).isInstanceOf(GameState.Running.BlackTurn::class.java)
    }

    @Test
    fun `오목이 되면 Finish 상태로 변한다`() {
        OMOK_FINISH.forEach { stone ->
            gameState =
                gameState.updateState(
                    { },
                    { },
                    { stone },
                )
        }
        assertThat(gameState).isInstanceOf(GameState.Finish::class.java)
    }
}
