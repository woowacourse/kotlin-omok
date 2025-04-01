package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.board.Point

class GameStateTest {
    @Test
    fun `초기 상태 테스트`() {
        val gameState = GameState()

        assertThat(gameState.isFinished).isEqualTo(false)
        assertThat(gameState.previousPoint).isNull()
    }

    @Test
    fun `마지막 이동 점 업데이트 테스트`() {
        val gameState = GameState()
        val point = Point(3, 4)

        gameState.updateLastMovePoint(point)

        assertThat(gameState.previousPoint).isEqualTo(point)
    }

    @Test
    fun `게임 종료 테스트`() {
        val gameState = GameState()

        gameState.finishGame()

        assertThat(gameState.isFinished).isEqualTo(true)
    }

    @Test
    fun `초기 값이 주어진 상태의 게임 테스트`() {
        val initialPoint = Point(1, 2)
        val gameState = GameState(false, initialPoint)

        assertThat(gameState.previousPoint).isEqualTo(initialPoint)
        assertThat(gameState.isFinished).isEqualTo(false)
    }
}
