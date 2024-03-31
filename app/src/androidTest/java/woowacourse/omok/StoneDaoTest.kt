package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import omok.model.Color
import omok.model.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

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
        stoneDao.insert(Stone(1, 1, Color.BLACK))
        val stones = stoneDao.stones()
        assertThat(stones).contains(Stone(1, 1, Color.BLACK))
    }

    @Test
    fun findAll() {
        val exampleStones =
            listOf(Stone(1, 2, Color.BLACK), Stone(1, 13, Color.BLACK), Stone(4, 5, Color.WHITE))

        exampleStones.forEach { stoneDao.insert(it) }

        val dbStones = stoneDao.stones()
        assertThat(dbStones).containsExactly(Stone(1, 2, Color.BLACK), Stone(1, 13, Color.BLACK), Stone(4, 5, Color.WHITE))
    }

    @Test
    fun deleteAll() {
        val exampleStones =
            listOf(Stone(1, 2, Color.BLACK), Stone(1, 13, Color.BLACK), Stone(4, 5, Color.WHITE))

        exampleStones.forEach { stoneDao.insert(it) }
        stoneDao.deleteAll()

        val dbStones = stoneDao.stones()
        assertThat(dbStones).isEmpty()
    }
}
