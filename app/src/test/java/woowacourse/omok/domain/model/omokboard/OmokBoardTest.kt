package woowacourse.omok.domain.model.omokboard

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokBoardTest {
    @Test
    fun `크기를 설정하지 않은 오목판은 15 x 15 사이즈이다`() {
        // given
        val actual = OmokGame().board.snapshot

        // when & then
        listOf(1, 15).forEach { row ->
            listOf(1, 15).forEach { column ->
                assertThat(actual).containsKey(Position(row, column))
            }
        }
        assertThat(actual.size).isEqualTo(15 * 15)
    }
}
