package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokGameTest {
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setUp() {
        omokGame = OmokGame()
    }

    @Test
    fun `수평 방향으로 오목인지 확인한다`() {
        omokGame.grid.putStone(Position(1, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(1, 2), StoneState.BLACK)
        omokGame.grid.putStone(Position(1, 3), StoneState.BLACK)
        omokGame.grid.putStone(Position(1, 4), StoneState.BLACK)
        omokGame.grid.putStone(Position(1, 5), StoneState.BLACK)

        assertThat(omokGame.checkOmok(Position(1, 5))).isTrue()
    }

    @Test
    fun `수직 방향으로 오목인지 확인한다`() {
        omokGame.grid.putStone(Position(1, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(2, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(3, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(4, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(5, 1), StoneState.BLACK)

        assertThat(omokGame.checkOmok(Position(5, 1))).isTrue()
    }

    @Test
    fun `대각선 아래 방향으로 오목인지 확인한다`() {
        omokGame.grid.putStone(Position(1, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(2, 2), StoneState.BLACK)
        omokGame.grid.putStone(Position(3, 3), StoneState.BLACK)
        omokGame.grid.putStone(Position(4, 4), StoneState.BLACK)
        omokGame.grid.putStone(Position(5, 5), StoneState.BLACK)

        assertThat(omokGame.checkOmok(Position(5, 5))).isTrue()
    }

    @Test
    fun `대각선 위 방향으로 오목인지 확인한다`() {
        omokGame.grid.putStone(Position(5, 1), StoneState.BLACK)
        omokGame.grid.putStone(Position(4, 2), StoneState.BLACK)
        omokGame.grid.putStone(Position(3, 3), StoneState.BLACK)
        omokGame.grid.putStone(Position(2, 4), StoneState.BLACK)
        omokGame.grid.putStone(Position(1, 5), StoneState.BLACK)

        assertThat(omokGame.checkOmok(Position(1, 5))).isTrue()
    }
}
