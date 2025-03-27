package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameDao

class GameListActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var gameDao: GameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_game_list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        gameDao = GameDao(dbHelper)

        val games = gameDao.queryGames()
        val gameAdapter =
            GameRecyclerAdapter(games) { gameId ->
                val intent =
                    Intent(this, GameActivity::class.java).apply {
                        putExtra("game_id", gameId.toLong())
                    }
                startActivity(intent)
            }

        findViewById<RecyclerView>(R.id.rv_game_list).apply {
            layoutManager = LinearLayoutManager(this@GameListActivity, RecyclerView.VERTICAL, false)
            adapter = gameAdapter
        }
    }
}
