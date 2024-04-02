package woowacourse.omok

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.local.db.FakeStoneDao
import woowacourse.omok.local.db.StoneDao
import woowacourse.omok.local.model.StoneEntity

@RunWith(AndroidJUnit4::class)
class StoneDaoTest {
    private lateinit var stoneDao: StoneDao

    @Before
    fun setUp() {
        stoneDao = FakeStoneDao()
    }


    @Test
    fun `돌을_저장하면_저장된_돌이_반환된다`() {
        // given
        val stoneEntity = StoneEntity(0, 5, 5)

        // when
        val savedStone = stoneDao.save(stoneEntity)

        // then
        assertThat(savedStone.id).isNotEqualTo(0L)
        assertThat(savedStone.x).isEqualTo(5)
        assertThat(savedStone.y).isEqualTo(5)
    }

    @Test
    fun `돌을_여러_개_저장한_후_모두_조회하면_모든_돌이_반환된다`() {
        // given
        val stoneEntity1 = StoneEntity(0, 1, 1)
        val stoneEntity2 = StoneEntity(0, 2, 2)

        // when
        stoneDao.save(stoneEntity1)
        stoneDao.save(stoneEntity2)
        val stones = stoneDao.findAll()

        // then
        assertThat(stones).hasSize(2)
        assertThat(stones.map { it.x }).containsExactlyInAnyOrder(1, 2)
        assertThat(stones.map { it.y }).containsExactlyInAnyOrder(1, 2)
    }

    @Test
    fun `모든_돌을_삭제하면_조회할_돌이_없다`() {
        // given
        stoneDao.save(StoneEntity(0, 3, 3))
        stoneDao.save(StoneEntity(0, 4, 4))

        // when
        stoneDao.drop()
        val stones = stoneDao.findAll()

        // then
        assertThat(stones).isEmpty()
    }
}
