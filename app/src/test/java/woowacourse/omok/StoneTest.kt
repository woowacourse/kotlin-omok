package woowacourse.omok

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class StoneTest {
    @Test
    fun `오목돌을 생성한다`() {
        assertDoesNotThrow {
            Stone(
                position = Position(Row.from(3), Col.from('C')),
                color = StoneColor.BLACK,
            )
        }
    }
}
