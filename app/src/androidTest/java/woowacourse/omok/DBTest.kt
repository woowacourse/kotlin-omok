package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.omok.model.db.StoneDao

class DBTest {
    private lateinit var stoneDao: StoneDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        stoneDao = StoneDao(context)
        stoneDao.drop()
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
    fun findAllStone() {
        val points = listOf(Point(2, 2), Point(3, 3), Point(4, 4))
        val expected = points.map { Stone(it, StoneColor.BLACK) }.toSet()
        expected.forEach { stoneDao.save(it) }
        val actual = stoneDao.findAll()
        assertThat(actual).isEqualTo(expected)
    }
}
