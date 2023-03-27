package domain.rule

import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class NotLongMokConstraintTest {

    @Test
    fun `연결된 돌의 개수가 6 이상이라면 제약조건을 만족하지 않는다`() {
        val stones = Stones().apply {
            add(Stone('A', 1))
            add(Stone('A', 2))
            add(Stone('A', 3))
            add(Stone('A', 4))
            add(Stone('A', 5))
            add(Stone('A', 6))
        }

        assertThat(NotLongMokConstraint.isSatisfied(stones, setOf())).isFalse
    }

    @Test
    fun `연결된 돌의 개수가 5 이하라면 제약조건을 만족한다`() {
        val stones = Stones().apply {
            add(Stone('A', 1))
            add(Stone('A', 2))
            add(Stone('A', 3))
            add(Stone('A', 4))
            add(Stone('A', 5))
        }

        assertThat(NotLongMokConstraint.isSatisfied(stones, setOf())).isTrue
    }
}