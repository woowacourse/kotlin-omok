package woowacourse.omok.view

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.omok.model.db.OmokDAO
import woowacourse.omok.model.db.OmokDbHelper
import woowacourse.omok.model.entity.Point

class OmokDaoTest {
    private lateinit var omokDao: OmokDAO

    @Before
    fun setUp() {
        val db = OmokDbHelper(ApplicationProvider.getApplicationContext())
        omokDao = OmokDAO(db.writableDatabase, "TEST")
    }

    @After
    fun tearDown() {
        omokDao.deleteAll()
    }

    @Test
    fun insertStone() {
        val actual = omokDao.insertStone(1, 1, "BLACK")

        assertThat(actual).isGreaterThan(0)
    }

    @Test
    fun saveAndFindAll() {
        val entry = omokDao.selectAll()
        val point = entry.first().point

        assertThat(point).isEqualTo(Point(1, 1))
    }
}
