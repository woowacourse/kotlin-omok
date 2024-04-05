package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.omok.db.OmokDbHelper
import woowacourse.omok.db.StoneDao

class DBTest {
    private lateinit var stoneDao: StoneDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        stoneDao = StoneDao(context, dbHelper = OmokDbHelper(context, "TEST"))
    }

    @After
    fun tearDown() {
        stoneDao.deleteAll()
    }

    @Test
    fun storeOneStone() {
        val point = Point(1, 1)
        val expected = Stone(point, StoneColor.BLACK)
        stoneDao.save(expected)
        val actual = stoneDao.findAt(point)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun updateDuplicatedStone() {
        val point = Point(1, 1)
        val expected = Stone(point, StoneColor.BLACK)
        stoneDao.save(Stone(point, StoneColor.WHITE))
        // stoneDao.update(expected)
        stoneDao.save(expected)
        val actual = stoneDao.findAt(point)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun findAllStone() {
        val points = listOf(Point(2, 2), Point(3, 3), Point(4, 4))
        val expected = points.map { Stone(it, StoneColor.BLACK) }.toSet()
        expected.forEach { stoneDao.save(it) }
        val actual = stoneDao.findAll()
        assertThat(actual).isEqualTo(expected)
    }
}
