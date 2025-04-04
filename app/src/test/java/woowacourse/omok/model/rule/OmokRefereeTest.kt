package woowacourse.omok.model.rule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.rule.mock.FakeOmokRule
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row

class OmokRefereeTest {
    private val fakeOmokRule = FakeOmokRule()
    private val emptyBoard = Board()
    private val stonePlacedBoard = emptyBoard.nextStonePlacedBoard(Position(Row(0), Col(0)))

    @Test
    fun `보드를 보고 오목 결과를 반환한다`() {
        val omokReferee = OmokReferee(fakeOmokRule)

        assertThat(omokReferee.isOmok(emptyBoard)).isFalse()

        assertThat(omokReferee.isOmok(stonePlacedBoard)).isTrue()
    }

    @Test
    fun `보드를 보고 마지막 돌의 금수 여부를 반환한다`() {
        val omokReferee = OmokReferee(fakeOmokRule)

        assertThat(omokReferee.lastStoneFoul(emptyBoard)).isEqualTo(RenjuFoul.SAFE)

        assertThat(omokReferee.lastStoneFoul(stonePlacedBoard)).isEqualTo(RenjuFoul.THREE_BY_THREE_FOUL)
    }
}
