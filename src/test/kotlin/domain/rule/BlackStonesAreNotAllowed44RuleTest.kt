package domain.rule

import domain.state.BlackTurn
import domain.state.State
import domain.stone.Stone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlackStonesAreNotAllowed44RuleTest {

    @ParameterizedTest
    @MethodSource("getStatesRightBeforeBlackStonesBecome44AndStone")
    fun `흑돌을 뒀을 때 44가 되면 이 규칙을 위반한다`(state: State, nextStone: Stone) {
        val actual = BlackStonesAreNotAllowed44Rule.stateWillObeyThisRule(state, nextStone)

        Assertions.assertThat(actual).isFalse
    }

    private fun getStatesRightBeforeBlackStonesBecome44AndStone() = Stream.of(
        Arguments.arguments(
            BlackTurn(
                setOf(
                    Stone('C', 12),
                    Stone('D', 12),
                    Stone('G', 12),
                    Stone('I', 12),
                    Stone('J', 12),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                    Stone('A', 6),
                ),
            ),
            Stone('F', 12),
        ),
        Arguments.arguments(
            BlackTurn(
                setOf(
                    Stone('C', 12),
                    Stone('C', 11),
                    Stone('C', 10),
                    Stone('E', 6),
                    Stone('F', 5),
                    Stone('G', 4),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                    Stone('A', 7),
                    Stone('A', 6),
                ),
            ),
            Stone('C', 8),
        ),
        Arguments.arguments(
            BlackTurn(
                setOf(
                    Stone('E', 5),
                    Stone('F', 5),
                    Stone('G', 5),
                    Stone('H', 6),
                    Stone('H', 7),
                    Stone('H', 8),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                    Stone('D', 5),
                    Stone('H', 9),
                ),
            ),
            Stone('H', 5),
        ),
        Arguments.arguments(
            BlackTurn(
                setOf(
                    Stone('F', 5),
                    Stone('H', 7),
                    Stone('J', 9),
                    Stone('H', 8),
                    Stone('J', 8),
                    Stone('K', 8),
                ),
                setOf(
                    Stone('A', 1),
                    Stone('A', 2),
                    Stone('A', 3),
                    Stone('A', 4),
                    Stone('A', 7),
                    Stone('A', 6),
                ),
            ),
            Stone('I', 8),
        ),
    )
}
