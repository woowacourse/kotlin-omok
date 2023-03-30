package domain.rule

import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Not33ConstraintTest {

    @ParameterizedTest
    @MethodSource("get33Stones")
    fun `돌들이 33이라면 제약조건을 만족하지 않는다`(stones: Stones) {
        assertThat(Not33Constraint.isSatisfied(stones, setOf())).isFalse
    }

    @Test
    fun `돌들이 33이 아니라면 제약조건을 만족한다`() {
        val stones = Stones().apply {
            add(Stone('C', 12))
            add(Stone('E', 12))
            add(Stone('D', 13))
            add(Stone('D', 14))
        }

        assertThat(Not33Constraint.isSatisfied(stones, setOf())).isTrue
    }

    private fun get33Stones() = Stream.of(
        arguments(Stones().apply {
            add(Stone('C', 12))
            add(Stone('E', 12))
            add(Stone('D', 13))
            add(Stone('D', 14))
            add(Stone('D', 12))
        }),
        arguments(Stones().apply {
            add(Stone('B', 6))
            add(Stone('C', 5))
            add(Stone('E', 6))
            add(Stone('E', 5))
            add(Stone('E', 3))
        }),
        arguments(Stones().apply {
            add(Stone('J', 9))
            add(Stone('N', 9))
            add(Stone('M', 10))
            add(Stone('M', 12))
            add(Stone('L', 11))
        }),
        arguments(Stones().apply {
            add(Stone('K', 6))
            add(Stone('K', 3))
            add(Stone('M', 4))
            add(Stone('N', 4))
            add(Stone('K', 4))
        }),
    )
}