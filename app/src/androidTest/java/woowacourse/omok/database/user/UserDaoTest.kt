package woowacourse.omok.database.user

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        userDao = UserDao(UserDbHelper(ApplicationProvider.getApplicationContext()))
        userDao.createTable()
    }

    @After
    fun tearDown() {
        userDao.drop()
    }

    @Test
    fun saveTest() {
        val user = User("test", 0, 0)
        val actual = userDao.save(user)
        Log.d("actual", actual.toString())
        assertAll(
            { assertThat(actual.id).isEqualTo(1) },
            { assertThat(actual.name).isEqualTo("test") },
            { assertThat(actual.winCount).isEqualTo(0) },
            { assertThat(actual.loseCount).isEqualTo(0) },
        )
    }

    @Test
    fun findAllTest() {
        val user1 = User("test1", 0, 0)
        val user2 = User("test2", 1, 2)
        val user3 = User("test3", 2, 1)

        userDao.save(user1)
        userDao.save(user2)
        userDao.save(user3)

        val users = userDao.findAll()

        users[0].let {
            assertThat(it.id).isEqualTo(1)
            assertThat(it.name).isEqualTo("test1")
            assertThat(it.winCount).isEqualTo(0)
            assertThat(it.loseCount).isEqualTo(0)
        }

        users[1].let {
            assertThat(it.id).isEqualTo(2)
            assertThat(it.name).isEqualTo("test2")
            assertThat(it.winCount).isEqualTo(1)
            assertThat(it.loseCount).isEqualTo(2)
        }

        users[2].let {
            assertThat(it.id).isEqualTo(3)
            assertThat(it.name).isEqualTo("test3")
            assertThat(it.winCount).isEqualTo(2)
            assertThat(it.loseCount).isEqualTo(1)
        }
    }
}
