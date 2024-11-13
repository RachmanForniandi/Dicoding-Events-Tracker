package rachman.forniandi.dicodingeventstracker.domain.Splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.domain.home.MainActivity
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SettingThemeViewModel
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SplashScreenViewModel
import java.util.Timer
import kotlin.concurrent.schedule

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Timer("splashGone", true).schedule(3000) {

            viewModel.getNightTheme().observe(this@SplashScreenActivity){ isActiveTheme->
                if (isActiveTheme){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
                viewModel.setNightTheme(isActiveTheme)
            }
        }
    }

}