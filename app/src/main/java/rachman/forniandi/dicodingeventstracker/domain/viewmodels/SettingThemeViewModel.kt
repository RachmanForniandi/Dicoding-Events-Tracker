package rachman.forniandi.dicodingeventstracker.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rachman.forniandi.dicodingeventstracker.domain.repository.SettingThemeRepository
import javax.inject.Inject

@HiltViewModel
class SettingThemeViewModel @Inject constructor(
    private val settingThemeRepository: SettingThemeRepository
) : ViewModel(){

    fun obtainTheme() = settingThemeRepository.getTheme().asLiveData()

    fun setTheme(isDarkModeThemeActivated:Boolean) = viewModelScope.launch {
        settingThemeRepository.setTheme(isDarkModeThemeActivated)
    }
}