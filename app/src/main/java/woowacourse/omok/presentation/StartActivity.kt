package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.omok.R
import woowacourse.omok.presentation.gameactivity.GameActivity
import woowacourse.omok.util.setOnSingleClickListener

class StartActivity : AppCompatActivity() {

    private lateinit var backgroundLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        getBackGroundLayout()
        initMoveGameActivity()
    }

    private fun getBackGroundLayout() {
        backgroundLayout = findViewById<ConstraintLayout>(R.id.layout_start)
    }

    private fun initMoveGameActivity() {
        backgroundLayout.setOnSingleClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
