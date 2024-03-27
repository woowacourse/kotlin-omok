package omok

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import omok.fixtures.createPosition
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PositionTest {
    @ParameterizedTest
    @CsvSource(value = ["1:1", "1:2", "15:15"], delimiter = ':')
    fun `Point 는 (15,15) 까지 싱글톤이다`(
        x: Int,
        y: Int,
    ) {
        createPosition(x, y) shouldBe createPosition(x, y)
        createPosition(x, y) shouldBeSameInstanceAs createPosition(x, y)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "15:16", "16:15", "16:16",
        ],
        delimiter = ':',
    )
    fun `x, y 는 (1, 1) ~ (15, 15) 사이가 아니면 새로 생성한다`(
        x: Int,
        y: Int,
    ) {
        createPosition(x, y) shouldNotBeSameInstanceAs createPosition(x, y)
    }
}
