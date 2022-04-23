package ui.smartpro.appselect.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ui.smartpro.appselect.MainActivity
import ui.smartpro.appselect.databinding.SplashActivityBinding
import ui.smartpro.common.base.BaseActivity

class SplashActivity : BaseActivity<SplashActivityBinding>() {

    var handler = Handler(Looper.getMainLooper())

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 1000)
    }

    override fun setBinding(): SplashActivityBinding = SplashActivityBinding.inflate(layoutInflater)

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}