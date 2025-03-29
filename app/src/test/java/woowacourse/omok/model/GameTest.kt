package woowacourse.omok.model

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import rule.BlackRenjuRule
import rule.wrapper.point.Point
import woowacourse.omok.fixture.A1
import woowacourse.omok.fixture.A2
import woowacourse.omok.fixture.A3
import woowacourse.omok.fixture.A4
import woowacourse.omok.fixture.A5
import woowacourse.omok.fixture.K1
import woowacourse.omok.fixture.K10
import woowacourse.omok.fixture.K11
import woowacourse.omok.fixture.M1
import woowacourse.omok.fixture.M11
import woowacourse.omok.mapper.BlackRuleChecker
import woowacourse.omok.mapper.PointMapper
import woowacourse.omok.model.game.Game
import woowacourse.omok.model.rule.PlacementError.AlreadyOccupiedViolation
import woowacourse.omok.model.stone.StoneColor

class GameTest {
    private val game =
        Game(
            blackRuleChecker =
                BlackRuleChecker(
                    BlackRenjuRule(),
                    mapper = PointMapper { pos -> Point(pos.col.value + 1, pos.row.value + 1) },
                ),
        )

    @Test
    fun `처음으로 착수하는 돌의 색을 확인할 수 있다`() {
        assertThat(game.turn).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `다음 턴으로 착수하는 돌의 색을 확인할 수 있다`() {
        val position = Position(Row(1), Col(2))
        game.applyPlacement(position)
        assertThat(game.turn).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val position = Position(Row(5), Col(5))

        assertDoesNotThrow { game.applyPlacement(position) }
    }

    @Test
    fun `마지막으로 착수한 돌을 확인할 수 있다`() {
        val position = Position(Row(5), Col(5))
        val stoneColor = game.turn
        game.applyPlacement(position)

        val lastStone = game.lastStone

        assertThat(lastStone?.position).isEqualTo(position)
        assertThat(lastStone?.stoneColor).isEqualTo(stoneColor)
    }

    @Test
    fun `착수한 후에 흑돌이 오목인지 아닌지 확인할 수 있다`() {
        val positions =
            listOf(
                A1,
                K1,
                A2,
                K10,
                A3,
                M1,
                A4,
                M11,
                A5,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(game.isOmok()).isTrue()
    }

    @Test
    fun `이미 돌이 있는 위치에 돌을 놓을 수 없다`() {
        val position = Position(Row(0), Col(0))
        game.playTurn(position)

        assertThat(
            game.playTurn(position),
        ).isEqualTo(AlreadyOccupiedViolation)
    }

    @Test
    fun `착수한 후에 백돌이 오목인지 아닌지 확인할 수 있다`() {
        val positions =
            listOf(
                K1,
                A1,
                K10,
                A2,
                M1,
                A3,
                M11,
                A4,
                K11,
                A5,
            )

        for (i in positions) {
            game.applyPlacement(i)
        }

        assertThat(game.isOmok()).isTrue()
    }
}
