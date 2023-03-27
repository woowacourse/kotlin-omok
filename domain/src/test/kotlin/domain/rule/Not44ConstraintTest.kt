package domain.rule

import domain.stone.Point
import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Not44ConstraintTest {

    @ParameterizedTest
    @MethodSource("get44Stones")
    fun `돌들이 44라면 제약조건을 만족하지 않는다`(stones: Stones, forbiddenPoints: Set<Point>) {
        assertThat(Not44Constraint.isSatisfied(stones, forbiddenPoints)).isFalse
    }

    @Test
    fun `돌들이 44가 아니라면 제약조건을 만족한다`() {
        val stones = Stones().apply {
            add(Stone('C', 12))
            add(Stone('D', 12))
            add(Stone('G', 12))
            add(Stone('I', 12))
            add(Stone('J', 12))
        }

        assertThat(Not44Constraint.isSatisfied(stones, setOf())).isTrue
    }

    private fun get44Stones() = Stream.of(
        arguments(Stones().apply {
            add(Stone('C', 12))
            add(Stone('D', 12))
            add(Stone('G', 12))
            add(Stone('I', 12))
            add(Stone('J', 12))
            add(Stone('F', 12))
        }, setOf<Point>()),
        arguments(Stones().apply {
            add(Stone('C', 12))
            add(Stone('C', 11))
            add(Stone('C', 10))
            add(Stone('E', 6))
            add(Stone('F', 5))
            add(Stone('G', 4))
            add(Stone('C', 8))
        }, setOf<Point>()),
        arguments(Stones().apply {
            add(Stone('E', 5))
            add(Stone('F', 5))
            add(Stone('G', 5))
            add(Stone('H', 6))
            add(Stone('H', 7))
            add(Stone('H', 8))
            add(Stone('H', 5))
        }, setOf(Point('D', 5), Point('H', 9))),
        arguments(Stones().apply {
            add(Stone('J', 6))
            add(Stone('J', 8))
            add(Stone('J', 9))
            add(Stone('J', 12))
            add(Stone('J', 10))
        }, setOf<Point>()),
        arguments(Stones().apply {
            add(Stone('J', 9))
            add(Stone('H', 7))
            add(Stone('F', 5))
            add(Stone('H', 8))
            add(Stone('J', 8))
            add(Stone('K', 8))
            add(Stone('I', 8))
        }, setOf<Point>()),
    )
}