package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.db.BoardDao
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameDao

class GameListActivity :
    AppCompatActivity(),
    OnGameDeleteListener {
    private val dbHelper: DbHelper by lazy { DbHelper(this) }
    private val boardDao: BoardDao by lazy { BoardDao(dbHelper) }
    private val gameDao: GameDao by lazy { GameDao(dbHelper, boardDao) }
    private lateinit var gameAdapter: GameRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_game_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val games = gameDao.queryGames()
        gameAdapter =
            GameRecyclerAdapter(
                games,
                onItemClick = { gameId -> navigateToGameActivity(gameId) },
                deleteListener = this,
            )

        findViewById<RecyclerView>(R.id.rv_game_list).adapter = gameAdapter
    }

    private fun navigateToGameActivity(gameId: Int) {
        val intent =
            Intent(this, GameActivity::class.java).apply {
                putExtra("game_id", gameId.toLong())
            }
        startActivity(intent)
    }

    override fun onDeleteGame(gameId: Int) {
        val result = gameDao.deleteGame(gameId)
        runOnUiThread {
            if (result) {
                Toast.makeText(this, R.string.text_delete_game, Toast.LENGTH_LONG).show()
                updateGameList(gameId)
            } else {
                Toast.makeText(this, R.string.text_delete_fail, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateGameList(gameId: Int) {
        gameAdapter.removeItem(gameId)
    }
}
