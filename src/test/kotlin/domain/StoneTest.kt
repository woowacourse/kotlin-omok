package domain

import domain.domain.Color
import domain.domain.Position2
import domain.domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StoneTest {
    @Test
    fun `바둑돌은 색상과 위치를 갖는다`() {
        // given
        val position = Position2(1, 1)
        val color = Color.BLACK
        // when
        val actual = Stone(color, position)
        // then
        assertThat(actual).isInstanceOf(Stone::class.java)
    }

    @CsvSource(value = ["BLACK,true", "WHITE,false"])
    @ParameterizedTest
    fun `바둑돌이 검정색인지 확인한다`(color: Color, expected: Boolean) {
        // given
        val stone = Stone(color, Position2(1, 1))
        // when
        val actual = stone.isBlack()
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @CsvSource(value = ["BLACK,false", "WHITE,true"])
    @ParameterizedTest
    fun `바둑돌이 흰색인지 확인한다`(color: Color, expected: Boolean) {
        // given
        val stone = Stone(color, Position2(1, 1))
        // when
        val actual = stone.isWhite()
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
