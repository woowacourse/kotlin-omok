package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.database.OmokDao
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.database.OmokEntity

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        omokDao = OmokDao(OmokDbHelper(context))
    }

    @Test
    fun insertDataTest() {
        omokDao.insertData(OmokEntity(1, 1, "BLACK"))
        val data: List<OmokEntity> = omokDao.queryAll()
        assertThat(data.size).isEqualTo(1)
        assertThat(data.first()).isEqualTo(OmokEntity(1, 1, "BLACK"))
    }

    @Test
    fun clearDataTest() {
        omokDao.insertData(OmokEntity(1, 1, "BLACK"))
        omokDao.clear()
        assertThat(omokDao.queryAll().size).isEqualTo(0)
    }
}
