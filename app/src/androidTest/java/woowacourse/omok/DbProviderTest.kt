package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.data.db.DbProvider
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone

class DbProviderTest {
    private lateinit var dbProvider: DbProvider

    @BeforeEach
    fun setUp() {
        dbProvider = DbProvider(FakeDbHelper(ApplicationProvider.getApplicationContext()))
        dbProvider.createTable()
    }

    @AfterEach
    fun dropDown() {
        dbProvider.dropTable()
        dbProvider.closeDB()
    }

    @Test
    @DisplayName("돌을 놓으면 테이블에 추가한다")
    fun addStoneToDB() {
        // given
        val stone = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        dbProvider.insertStone(stone)

        // when
        val actual = dbProvider.readAll().contains(stone)

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("디비에 저장된 돌 리스트를 반환한다")
    fun getStonesInDb() {
        // given
        val stone1 = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        val stone2 = Stone(Point(Row(2), Column(2)), StoneColor.WHITE)

        dbProvider.insertStone(stone1)
        dbProvider.insertStone(stone2)

        // when
        val actual = dbProvider.readAll().containsAll(listOf(stone1, stone2))

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("디비에 저장된 돌 리스트를 삭제한다")
    fun dropTable() {
        // given
        val stone1 = Stone(Point(Row(1), Column(2)), StoneColor.BLACK)
        val stone2 = Stone(Point(Row(2), Column(2)), StoneColor.WHITE)

        dbProvider.insertStone(stone1)
        dbProvider.insertStone(stone2)

        // when
        dbProvider.dropTable()
        val actual = dbProvider.readAll()

        // then
        assertThat(actual).isEmpty()
    }
}
