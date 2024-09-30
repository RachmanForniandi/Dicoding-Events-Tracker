package rachman.forniandi.dicodingeventstracker.domain.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.databinding.FragmentSettingsBinding
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.PastEventsViewmodel
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SettingThemeViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding?= null
    private val binding get() = _binding!!
    private lateinit var settingThemeViewModel: SettingThemeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =FragmentSettingsBinding.inflate(inflater, container, false)
        settingThemeViewModel = ViewModelProvider(this)[SettingThemeViewModel::class.java]
        setupSwitchTheme()
        return binding.root
    }

    private fun setupSwitchTheme() {
        with(binding) {
            switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
                settingThemeViewModel.setTheme(isChecked)
            }
            settingThemeViewModel.obtainTheme().observe(requireActivity()){ isDarkThemeActivated->
                switchChangeTheme.isChecked = isDarkThemeActivated
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkThemeActivated)AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )

            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBackSetting.setOnClickListener {
                activity?.onBackPressed()
            }

            lineOptionSettingChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}