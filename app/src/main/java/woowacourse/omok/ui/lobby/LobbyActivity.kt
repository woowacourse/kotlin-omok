package woowacourse.omok.ui.lobby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.databinding.ActivityLobbyBinding
import woowacourse.omok.domain.model.omokboard.OmokGameEntity
import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.ui.game.OmokGameActivity

class LobbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLobbyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val omokGameAdapter = OmokGameAdapter(::navigateToOmokGame)
        binding.rvLobbyRooms.adapter = omokGameAdapter

        val dummyGames =
            List(10) { index ->
                OmokGameEntity(
                    id = index,
                    host = PlayerName("Player$index"),
                )
            }

        omokGameAdapter.submitList(dummyGames)
    }

    private fun navigateToOmokGame(gameId: Int) {
        val intent = OmokGameActivity.getIntent(this, gameId)
        startActivity(intent)
    }
}
