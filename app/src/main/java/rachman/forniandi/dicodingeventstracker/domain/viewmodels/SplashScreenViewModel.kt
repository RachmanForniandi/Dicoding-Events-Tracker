package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.domain.repository.SettingThemeRepository
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val settingThemeRepository: SettingThemeRepository,
    application: Application
) : AndroidViewModel(application){

    fun getNightTheme() = settingThemeRepository.getTheme().asLiveData()

    fun setNightTheme(isDarkModeThemeActivated:Boolean) = viewModelScope.launch {
        settingThemeRepository.setTheme(isDarkModeThemeActivated)
    }

}