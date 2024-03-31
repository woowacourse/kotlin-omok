package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RenjuRuleAdapterTest {
    private lateinit var adapter: RenjuRuleAdapter
    private lateinit var stones: MutableList<Stone>

    @BeforeEach
    fun setUp() {
        adapter = RenjuRuleAdapter()
        stones = mutableListOf()
    }

    @Test
    fun `흑이 3-3 렌주룰을 어겼는지 확인한다_1`() {
        stones.apply {
            add(Stone(2, 2, Color.BLACK))
            add(Stone(2, 3, Color.BLACK))
            add(Stone(3, 4, Color.BLACK))
            add(Stone(4, 4, Color.BLACK))
        }
        val result = adapter.isInValid(stones, Stone(2, 4, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 3-3 렌주룰을 어겼는지 확인한다_2`() {
        stones.apply {
            add(Stone(6, 2, Color.BLACK))
            add(Stone(5, 3, Color.BLACK))
            add(Stone(5, 5, Color.BLACK))
            add(Stone(6, 5, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(3, 5, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 3-3 렌주룰을 어겼는지 확인한다_3`() {
        stones.apply {
            add(Stone(5, 10, Color.BLACK))
            add(Stone(2, 10, Color.BLACK))
            add(Stone(3, 12, Color.BLACK))
            add(Stone(3, 13, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(3, 10, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 3-3 렌주룰을 어겼는지 확인한다`() {
        stones.apply {
            add(Stone(8, 9, Color.BLACK))
            add(Stone(11, 12, Color.BLACK))
            add(Stone(9, 12, Color.BLACK))
            add(Stone(8, 13, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(10, 11, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 4-4 렌주룰을 어겼는지 확인한다_1`() {
        stones.apply {
            add(Stone(2, 12, Color.BLACK))
            add(Stone(3, 12, Color.BLACK))
            add(Stone(6, 12, Color.BLACK))
            add(Stone(8, 12, Color.BLACK))
            add(Stone(9, 12, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(5, 12, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 4-4 렌주룰을 어겼는지 확인한다_2`() {
        stones.apply {
            add(Stone(2, 14, Color.BLACK))
            add(Stone(2, 13, Color.BLACK))
            add(Stone(2, 11, Color.BLACK))
            add(Stone(2, 10, Color.BLACK))
            add(Stone(2, 9, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(2, 12, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 4-4 렌주룰을 어겼는지 확인한다_3`() {
        stones.apply {
            add(Stone(2, 11, Color.BLACK))
            add(Stone(2, 10, Color.BLACK))
            add(Stone(2, 9, Color.BLACK))
            add(Stone(4, 5, Color.BLACK))
            add(Stone(5, 4, Color.BLACK))
            add(Stone(6, 3, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(2, 7, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 4-4 렌주룰을 어겼는지 확인한다_4`() {
        stones.apply {
            add(Stone(4, 4, Color.BLACK))
            add(Stone(5, 4, Color.BLACK))
            add(Stone(6, 4, Color.BLACK))
            add(Stone(7, 5, Color.BLACK))
            add(Stone(7, 6, Color.BLACK))
            add(Stone(7, 7, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(7, 4, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 4-4 렌주룰을 어겼는지 확인한다_5`() {
        stones.apply {
            add(Stone(5, 4, Color.BLACK))
            add(Stone(7, 6, Color.BLACK))
            add(Stone(7, 7, Color.BLACK))
            add(Stone(9, 7, Color.BLACK))
            add(Stone(10, 7, Color.BLACK))
            add(Stone(9, 8, Color.BLACK))
        }

        val result = adapter.isInValid(stones, Stone(8, 7, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 장목 렌주룰을 어겼는지 확인한다`() {
        stones.apply {
            add(Stone(5, 4, Color.BLACK))
            add(Stone(6, 4, Color.BLACK))
            add(Stone(7, 4, Color.BLACK))
            add(Stone(9, 4, Color.BLACK))
            add(Stone(10, 4, Color.BLACK))
            add(Stone(11, 4, Color.BLACK))
        }


        val result = adapter.isInValid(this.stones, Stone(11, 4, Color.BLACK))
        assertThat(result).isTrue
    }

    @Test
    fun `흑이 이겼는지 확인한다`() {
        stones.apply {
            add(Stone(5, 4, Color.BLACK))
            add(Stone(6, 4, Color.BLACK))
            add(Stone(7, 4, Color.BLACK))
            add(Stone(9, 4, Color.BLACK))
        }

        val result = adapter.isBlackWin(stones, Point(8, 4))
        assertThat(result).isTrue

    }

    @Test
    fun `백이 이겼는지 확인한다`() {
        stones.apply {
            add(Stone(5, 4, Color.WHITE))
            add(Stone(6, 4, Color.WHITE))
            add(Stone(7, 4, Color.WHITE))
            add(Stone(9, 4, Color.WHITE))
            add(Stone(10, 4, Color.WHITE))
        }

        val result = adapter.isWhiteWin(stones, Point(8, 4))
        assertThat(result).isTrue
    }
}
