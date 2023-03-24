package domain.rule

import domain.state.BlackTurn
import domain.stone.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlackStonesAreNotAllowedLongMokTest {

    @Test
    fun `흑돌을 뒀을 때 롱목이 되면 이 규칙을 위반한다`() {
        val state = BlackTurn(
            setOf(
                Stone('C', 10),
                Stone('C', 11),
                Stone('C', 12),
                Stone('C', 14),
                Stone('C', 15),
            ),
            setOf(
                Stone('A', 10),
                Stone('A', 11),
                Stone('A', 12),
                Stone('A', 14),
                Stone('A', 15),
            ),
        )
        val nextStone = Stone('C', 13)

        val actual = BlackStonesAreNotAllowedLongMok.stateWillObeyThisRule(state, nextStone)

        Assertions.assertThat(actual).isFalse
    }
}
