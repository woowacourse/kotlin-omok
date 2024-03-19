package omok.study

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Map {
    @Test
    fun `mutableMap 은 순서 보장함`() {
        // given
        val map =
            mutableMapOf<Pair<Int, Int>, Int>(
                (1 to 1) to 1,
                (2 to 2) to 2,
                (3 to 3) to 3,
            )
        val ekeys = listOf(1 to 1, 2 to 2, 3 to 3)
        val evalues = listOf(1, 2, 3)
        // when
        val aKeys = map.keys.toList()
        val aValues = map.values.toList()
        // then
        aKeys shouldBe ekeys
        aValues shouldBe evalues
    }

    @Test
    fun `mutableMap 은 순서 보장함2`() {
        // given
        val map =
            mutableMapOf<Pair<Int, Int>, Int>(
                (1 to 1) to 1,
                (2 to 2) to 2,
                (3 to 3) to 3,
            )
        val eKey = (3 to 3)
        val eValue = 3
        // when
        val aKey = map.entries.last().key
        val aValue = map.entries.last().value
        // then
        aKey shouldBe eKey
        aValue shouldBe eValue
    }
}
