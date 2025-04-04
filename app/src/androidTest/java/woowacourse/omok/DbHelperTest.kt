package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import woowacourse.omok.data.DbHelper
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class DbHelperTest {
    private lateinit var dbHelper: DbHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DbHelper(context, "OmokTest.db")
    }

    @AfterEach
    fun dropDown() {
        dbHelper.deleteStones()
    }

    @Test
    @DisplayName("오목돌을 테이블에 추가한다")
    fun insertStone() {
        // given
        val stone = Stone(StoneColor.BLACK, Point(0, 0))
        dbHelper.insertStone(stone)

        // when
        val actual = dbHelper.queryStones().stones.contains(stone)

        // then
        assertThat(actual).isTrue()
    }

    @Test
    @DisplayName("DB에 저장된 모든 오목돌을 반환한다")
    fun queryStones() {
        // given
        val stone1 = Stone(StoneColor.BLACK, Point(0, 0))
        val stone2 = Stone(StoneColor.WHITE, Point(0, 1))

        dbHelper.insertStone(stone1)
        dbHelper.insertStone(stone2)

        // when
        val actual = dbHelper.queryStones().stones

        // then
        assertThat(actual).isEqualTo(setOf(stone1, stone2))
    }

    @Test
    @DisplayName("DB에 마지막으로 저장된 오목돌을 반환한다")
    fun queryLastStone() {
        // given
        val stone1 = Stone(StoneColor.BLACK, Point(0, 0))
        val stone2 = Stone(StoneColor.WHITE, Point(0, 1))

        dbHelper.insertStone(stone1)
        dbHelper.insertStone(stone2)

        // when
        val actual = dbHelper.queryLastStone()

        // then
        assertThat(actual).isEqualTo(stone2)
    }

    @Test
    @DisplayName("DB에 저장된 오목돌을 삭제한다")
    fun deleteStones() {
        // given
        val stone1 = Stone(StoneColor.BLACK, Point(0, 0))
        val stone2 = Stone(StoneColor.WHITE, Point(0, 1))

        dbHelper.insertStone(stone1)
        dbHelper.insertStone(stone2)

        // when
        dbHelper.deleteStones()
        val actual = dbHelper.queryStones().stones

        // then
        assertThat(actual).isEmpty()
    }
}
