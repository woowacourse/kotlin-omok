package woowacourse.omok.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.App
import woowacourse.omok.R
import woowacourse.omok.domain.Games
import woowacourse.omok.domain.event.GamesEvent
import woowacourse.omok.domain.event.GamesItemEvent
import woowacourse.omok.domain.model.Game
import woowacourse.omok.ui.IntentKeys.GAME_ID
import woowacourse.omok.ui.mapper.toGameUiModel

class OmokGamesActivity : AppCompatActivity() {
    private lateinit var omokGamesView: RecyclerView
    private lateinit var addOmokGame: Button
    private lateinit var games: Games
    private lateinit var omokGamesAdapter: OmokGamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omok_games)
        omokGamesView = findViewById(R.id.omok_games)
        addOmokGame = findViewById(R.id.add_game)
        omokGamesAdapter = OmokGamesAdapter(gamesItemEvent())
        games =
            Games(
                (application as App).gameRepository, (application as App).stoneRepository,
                gamesEvent(),
            )
        omokGamesView.layoutManager = LinearLayoutManager(this)
        omokGamesView.adapter = omokGamesAdapter
        games.update()
        addOmokGame.setOnClickListener {
            showCreateGameDialog { name -> games.insert(name) }
        }
    }

    private fun showCreateGameDialog(onCreate: (String) -> Unit) {
        val editText =
            EditText(this).apply {
                hint = getString(R.string.input_room_name)
                inputType = InputType.TYPE_CLASS_TEXT
            }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.make_room))
            .setView(editText)
            .setPositiveButton(getString(R.string.create_room_button)) { _, _ ->
                val name = editText.text.toString()
                onCreate(name)
                games.update()
            }
            .setNegativeButton(getString(R.string.cancel_room_button), null)
            .show()
    }

    private fun gamesItemEvent() =
        object : GamesItemEvent {
            override fun onGame(id: Long) {
                val intent = Intent(this@OmokGamesActivity, MainActivity::class.java)
                intent.putExtra(GAME_ID, id)
                startActivity(intent)
            }

            override fun onDelete(id: Long) {
                games.deleteGame(id)
                games.update()
            }
        }

    private fun gamesEvent() =
        object : GamesEvent {
            override fun updateGames(games: List<Game>) {
                omokGamesAdapter.submitList(games.map { it.toGameUiModel() })
            }
        }
}
