package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import woowacourse.omok.domain.model.fixture.createDrawBoard
import woowacourse.omok.domain.model.fixture.createGameOverBoard
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.Column
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position
import woowacourse.omok.domain.omok.model.Row

class BoardTest {
    @Test
    fun `이미 돌이 있는 자리에 착수를 진행하면, 예외를 발생시켜야 한다`() {
        // given
        val board = Board()
        // when
        board.place(Position(Row.ONE, Column.A))
        assertThrows<IllegalArgumentException> {
            board.place(Position(Row.ONE, Column.A))
        }
    }

    @Test
    fun `플레이어가 번갈아가며 착수할 수 있어야 한다`() {
        // given
        val board = Board()
        // when
        board.place(Position(Row.TEN, Column.B))
        board.place(Position(Row.ONE, Column.A))
        // then
        assertThat(board.notation.last().color).isEqualTo(Color.WHITE)
    }

    @ParameterizedTest
    @CsvSource(
        "3,B,WINNER_BLACK",
        "9,D,WINNER_WHITE",
        "1,O,PROCEEDING",
    )
    fun `게임 도중 오목이 완성되면 게임을 종료하고 승자를 반환해야 한다`(
        row: Int,
        col: Char,
        gameResult: GameResult,
    ) {
        // given
        val board = Board(_status = createGameOverBoard())
        // when
        val result = board.getGameResult(Position.of(row, col))
        // then
        assertThat(result).isEqualTo(gameResult)
    }

    @Test
    fun `같은돌 연속 5개가 완성되지 않으면서 판이 모두 채워졌다면 무승부를 반환해야 한다`() {
        // given
        val board = Board(_status = createDrawBoard())
        // when
        val result = board.getGameResult(Position.of(1, 'A'))
        // then
        assertThat(result).isEqualTo(GameResult.DRAW)
    }
}
