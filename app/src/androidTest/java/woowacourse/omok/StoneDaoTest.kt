package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import omok.model.Color
import omok.model.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoneDaoTest {
    private lateinit var stoneDao: StoneDao

    @Before
    fun setUp() {
        stoneDao = StoneDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        stoneDao.deleteAll()
    }

    @Test
    fun insert() {
        stoneDao.insert(exampleStone)
        val stones = stoneDao.stones()
        assertThat(stones).isEqualTo(listOf(exampleStone))
    }

    @Test
    fun findAll() {
        exampleStones.forEach { stoneDao.insert(it) }

        val dbStones = stoneDao.stones()
        assertThat(dbStones).isEqualTo(exampleStones)
    }

    @Test
    fun deleteAll() {
        exampleStones.forEach { stoneDao.insert(it) }
        stoneDao.deleteAll()

        val dbStones = stoneDao.stones()
        assertThat(dbStones).isEmpty()
    }

    companion object {
        private val exampleStone = Stone(1, 1, Color.BLACK)
        private val exampleStones =
            listOf(Stone(1, 2, Color.BLACK), Stone(1, 13, Color.BLACK), Stone(4, 5, Color.WHITE))
    }
}
