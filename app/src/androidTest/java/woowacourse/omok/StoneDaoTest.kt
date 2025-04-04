package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.data.StoneDao

class StoneDaoTest {
    private val testDbHelper = TestDbHelper(ApplicationProvider.getApplicationContext())
    private val stoneDao: StoneDao = StoneDao(testDbHelper)

    @DisplayName("돌을 추가한다")
    @Test
    fun `insert`() {
        stoneDao.insert(STONE_1B_WHITE)
        stoneDao.insert(STONE_10A_BLACK)
        val expected = 2

        val stonesInDatabase = stoneDao.findAllStone()
        assertThat(stonesInDatabase.size).isEqualTo(expected)
    }

    @DisplayName("존재하는 돌을 모두 가져온다")
    @Test
    fun `fetchAllStones`() {
        stoneDao.insert(STONE_10E_WHITE)
        stoneDao.insert(STONE_10E_BLACK)
        stoneDao.insert(STONE_1B_WHITE)
        stoneDao.insert(STONE_10A_BLACK)
        val expected = listOf(STONE_10E_WHITE, STONE_10E_BLACK, STONE_1B_WHITE, STONE_10A_BLACK)

        val stonesInDatabase = stoneDao.findAllStone()

        val isSatisfied =
            stonesInDatabase.all { stoneInDatabase ->
                expected.any { stone ->
                    stone.position.isSame(stoneInDatabase.position) && stone.color == stoneInDatabase.color
                }
            }
        assertAll(
            { assertThat(isSatisfied).isTrue() },
            { assertThat(stonesInDatabase.size).isEqualTo(expected.size) },
        )
    }

    @DisplayName("돌을 전부 제거한다")
    @Test
    fun `deleteAll`() {
        stoneDao.insert(STONE_1G_WHITE)
        stoneDao.insert(STONE_2G_BLACK)

        stoneDao.deleteAll()
        val actualStones = stoneDao.findAllStone()

        assertThat(actualStones.isEmpty()).isTrue()
    }
}
