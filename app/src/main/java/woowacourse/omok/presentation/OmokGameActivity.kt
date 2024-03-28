package woowacourse.omok.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import omok.model.OMokGame
import woowacourse.omok.local.OmokDbHelper

abstract class OmokGameActivity(private val layoutResID: Int) : AppCompatActivity() {
    abstract var db: OmokDbHelper
    abstract var oMokGame: OMokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)
        db = OmokDbHelper(this)
        initStartView()
    }

    abstract fun initStartView()

    abstract fun initOmok()

    abstract fun resetOmok()

    abstract fun setupBoardClickListener()

    fun showSuccessSnackbar(
        view: View,
        message: String,
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
