package domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TeamTest {

    @Test
    fun `흑팀의 다음 팀은 백팀이다`() {
        assertThat(Team.BLACK.next()).isEqualTo(Team.WHITE)
    }

    @Test
    fun `흑팀의 이전 팀은 백팀이다`() {
        assertThat(Team.BLACK.previous()).isEqualTo(Team.WHITE)
    }

    @Test
    fun `백팀의 다음 팀은 흑팀이다`() {
        assertThat(Team.WHITE.next()).isEqualTo(Team.BLACK)
    }

    @Test
    fun `백팀의 이전 팀은 흑팀이다`() {
        assertThat(Team.WHITE.previous()).isEqualTo(Team.BLACK)
    }
}