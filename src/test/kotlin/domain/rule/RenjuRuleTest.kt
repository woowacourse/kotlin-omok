package domain.rule

import domain.stone.Stone
import domain.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RenjuRuleTest {
    private lateinit var renjuRule: OmokRule

    @BeforeEach
    fun setUp() {
        renjuRule = RenjuRule()
    }

    @ParameterizedTest
    @CsvSource("3, 5", "12, 4", "3, 5", "4, 11", "11, 12")
    fun `3-3 테스트`() {
        val blackStones = Stones(
            Stone.of(3, 3),
            Stone.of(3, 4),
            Stone.of(4, 4),
            Stone.of(5, 3),
            Stone.of(12, 3),
            Stone.of(12, 5),
            Stone.of(13, 4),
            Stone.of(14, 4),
            Stone.of(6, 2),
            Stone.of(5, 5),
            Stone.of(6, 5),
            Stone.of(3, 11),
            Stone.of(6, 11),
            Stone.of(4, 13),
            Stone.of(4, 14),
            Stone.of(9, 14),
            Stone.of(10, 13),
            Stone.of(12, 13),
            Stone.of(9, 10),
        )
        val whiteStones = Stones(Stone.of(9, 9))
        val newStone = Stone.of(3, 5)

        val expected = renjuRule.check(blackStones, whiteStones, newStone)
        assertThat(expected).isTrue
    }

    @ParameterizedTest
    @CsvSource("13, 3", "8, 3", "12, 6", "10, 10", "8, 9", "5, 8")
    fun `4-4 테스트`(newStoneRow: Int, newStoneCol: Int) {
        val blackStones = Stones(
            Stone.of(15, 3),
            Stone.of(14, 3),
            Stone.of(12, 3),
            Stone.of(11, 3),
            Stone.of(10, 3),
            Stone.of(12, 4),
            Stone.of(12, 7),
            Stone.of(12, 9),
            Stone.of(12, 10),
            Stone.of(9, 10),
            Stone.of(8, 10),
            Stone.of(6, 10),
            Stone.of(8, 11),
            Stone.of(8, 8),
            Stone.of(7, 8),
            Stone.of(6, 8),
            Stone.of(6, 5),
            Stone.of(5, 5),
            Stone.of(5, 6),
            Stone.of(5, 7),
            Stone.of(4, 7),
        )
        val whiteStones = Stones(
            Stone.of(5, 4),
            Stone.of(9, 8),
        )
        val newStone = Stone.of(newStoneRow, newStoneCol)

        val expected = renjuRule.check(blackStones, whiteStones, newStone)
        assertThat(expected).isTrue
    }

    @Test
    fun `장목 테스트`() {
        val blackStones = Stones(
            Stone.of(5, 5),
            Stone.of(6, 6),
            Stone.of(7, 7),
            Stone.of(9, 9),
            Stone.of(10, 10),
        )
        val whiteStones = Stones()
        val newStone = Stone.of(8, 8)

        val expected = renjuRule.check(blackStones, whiteStones, newStone)
        assertThat(expected).isTrue
    }
}
