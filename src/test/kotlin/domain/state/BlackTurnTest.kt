package domain.state

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.Position
import domain.domain.Stones
import domain.domain.state.BlackTurn
import domain.domain.state.BlackWin
import domain.domain.state.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BlackTurnTest {
    @Test
    fun `black to black - 33금수일 경우 같은 상태를 반환한다`() {
        val blackTurn = BlackTurn(getThreeForbiddenStones())
        val forbiddenPosition = getForbiddenPosition()

        val actual = blackTurn.toNextState(forbiddenPosition)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `black to black - 44금수일 경우 같은 상태를 반환한다`() {
        val blackTurn = BlackTurn(getFourForbiddenStones())
        val forbiddenPosition = getForbiddenPosition()

        val actual = blackTurn.toNextState(forbiddenPosition)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `black to black - 장목 금수일 경우 같은 상태를 반환한다`() {
        val blackTurn = BlackTurn(getOverLinedStones(BLACK))
        val forbiddenPosition = getOverLinedPosition()

        val actual = blackTurn.toNextState(forbiddenPosition)

        assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `black to white - 승리가 아니고 문제없이 돌을 놓을 경우 반대 턴으로 넘어간다`() {
        val blackTurn = BlackTurn(Stones())

        val actual = blackTurn.toNextState(Position(1, 1))

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `black to blackWin - 블랙턴에서 오목을 만들면 블랙윈으로 넘어간다`() {
        val blackTurn = BlackTurn(getWinStones(BLACK))

        val actual = blackTurn.toNextState(getWinPosition())

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    companion object {
        fun getThreeForbiddenStones(): Stones {
            val stones = Stones()
            with(stones) {
                addStone(Position(4, 3), BLACK)
                addStone(Position(5, 3), BLACK)
                addStone(Position(3, 4), BLACK)
                addStone(Position(3, 5), BLACK)
            }
            return stones
        }

        fun getForbiddenPosition() = Position(3, 3)

        fun getFourForbiddenStones(): Stones {
            val stones = Stones()
            with(stones) {
                addStone(Position(4, 3), BLACK)
                addStone(Position(5, 3), BLACK)
                addStone(Position(6, 3), BLACK)
                addStone(Position(3, 4), BLACK)
                addStone(Position(3, 5), BLACK)
                addStone(Position(3, 6), BLACK)
            }
            return stones
        }

        fun getOverLinedStones(color: CoordinateState): Stones {
            val stones = Stones()
            with(stones) {
                addStone(Position(1, 1), color)
                addStone(Position(1, 2), color)
                addStone(Position(1, 3), color)
                addStone(Position(1, 5), color)
                addStone(Position(1, 6), color)
            }
            return stones
        }

        fun getOverLinedPosition(): Position = Position(1, 4)

        fun getWinStones(color: CoordinateState): Stones {
            val stones = Stones()
            with(stones) {
                addStone(Position(1, 2), color)
                addStone(Position(1, 3), color)
                addStone(Position(1, 4), color)
                addStone(Position(1, 5), color)
            }
            return stones
        }

        fun getWinPosition() = Position(1, 6)
    }
}
