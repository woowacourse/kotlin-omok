package domain.rule

import domain.Stone
import domain.state.BlackTurn
import domain.state.State
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlackStonesAreNotAllowed33RuleTest {

    @ParameterizedTest
    @MethodSource("getStatesRightBeforeBlackStonesBecome33AndStone")
    fun `흑돌을 뒀을 때 33이 되면 이 규칙을 위반한다`(state: State, nextStone: Stone) {
        val actual = BlackStonesAreNotAllowed33Rule.stateWillObeyThisRule(state, nextStone)

        assertThat(actual).isFalse
    }

    private fun getStatesRightBeforeBlackStonesBecome33AndStone() = Stream.of(
        arguments(
            BlackTurn(
                setOf(
                    Stone('D', 14),
                    Stone('D', 13),
                    Stone('C', 12),
                    Stone('E', 12),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                ),
            ),
            Stone('D', 12),
        ),
        arguments(
            BlackTurn(
                setOf(
                    Stone('B', 6),
                    Stone('C', 5),
                    Stone('E', 6),
                    Stone('E', 5),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                ),
            ),
            Stone('E', 3),
        ),
        arguments(
            BlackTurn(
                setOf(
                    Stone('J', 9),
                    Stone('N', 9),
                    Stone('M', 10),
                    Stone('M', 12),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                ),
            ),
            Stone('L', 11),
        ),
        arguments(
            BlackTurn(
                setOf(
                    Stone('K', 6),
                    Stone('K', 3),
                    Stone('M', 4),
                    Stone('N', 4),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                ),
            ),
            Stone('K', 4),
        ),
    )
}
