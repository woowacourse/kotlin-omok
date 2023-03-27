package woowacourse.omok.presentation.chooseroomactivity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.db.OmokDbHelper

class ChooseRoomActivity : AppCompatActivity() {

    private lateinit var btnNewGame: Button
    private lateinit var rvGameList: RecyclerView

    private val omokDbHelper = OmokDbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_room)
    }

    private fun initViewId() {
        btnNewGame = findViewById(R.id.btn_new_game)
        rvGameList = findViewById((R.id.rv_game_list))
    }
}
