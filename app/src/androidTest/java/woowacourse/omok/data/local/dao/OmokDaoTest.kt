package woowacourse.omok.data.local.dao

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.local.entity.OmokEntity

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {

    lateinit var omokDao: OmokDao
    @Before
    fun setUp() {
        omokDao = OmokDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        omokDao.drop()
    }

    @Test
    fun save(){
        val actual = omokDao.save(OmokEntity(2,3))

        assertThat(actual.id).isGreaterThan(0)
        assertThat(actual.x).isEqualTo(2)
        assertThat(actual.y).isEqualTo(3)
    }

    @Test
    fun findAll(){
        omokDao.save(OmokEntity(2,3))
        omokDao.save(OmokEntity(4,5))

        val actual = omokDao.findAll()

        assertThat(actual).isNotEmpty()
        assertThat(actual.size).isEqualTo(2)
    }
}