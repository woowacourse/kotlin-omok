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

    @DisplayName("두 개의 돌을 두면 DB에서 두 돌에 대한 정보 조회가 가능해야 한다")
    @Test
    fun `insert`() {
        stoneDao.insert(STONE_1B_WHITE)
        stoneDao.insert(STONE_10A_BLACK)

        val stonesInDatabase = stoneDao.findAllStone()
        val (actualStoneFirst, actualStoneSecond) = stonesInDatabase
        assertAll(
            { assertThat(actualStoneFirst.color).isEqualTo(STONE_1B_WHITE.color) },
            { assertThat(actualStoneFirst.position.col.value).isEqualTo(STONE_1B_WHITE.position.col.value) },
            { assertThat(actualStoneFirst.position.row.value).isEqualTo(STONE_1B_WHITE.position.row.value) },
            { assertThat(actualStoneSecond.color).isEqualTo(STONE_10A_BLACK.color) },
            { assertThat(actualStoneSecond.position.col.value).isEqualTo(STONE_10A_BLACK.position.col.value) },
            { assertThat(actualStoneSecond.position.row.value).isEqualTo(STONE_10A_BLACK.position.row.value) },
        )
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
