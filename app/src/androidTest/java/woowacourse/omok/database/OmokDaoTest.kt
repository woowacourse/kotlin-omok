package woowacourse.omok.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dbHelper = DbHelper(context, "TestOmok.db")
        omokDao = OmokDao(dbHelper)
    }

    @After
    fun clearDatabase() {
        omokDao.deleteStones()
    }

    @Test
    fun insertStone() {
        omokDao.insertStone(Stone(1, 1, StoneColor.BLACK))

        val actual: Set<Stone> = omokDao.readStones().stones

        val expected = setOf(Stone(1, 1, StoneColor.BLACK))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun deleteStones() {
        omokDao.insertStone(Stone(1, 1, StoneColor.BLACK))
        omokDao.deleteStones()
        val actual: Set<Stone> = omokDao.readStones().stones

        val expected = setOf<Stone>()

        assertThat(actual).isEqualTo(expected)
    }
}
