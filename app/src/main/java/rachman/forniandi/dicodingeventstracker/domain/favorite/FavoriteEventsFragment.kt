package rachman.forniandi.dicodingeventstracker.domain.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rachman.forniandi.dicodingeventstracker.databinding.FragmentFavoriteEventsBinding


class FavoriteEventsFragment : Fragment() {

    private var _binding: FragmentFavoriteEventsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteEventsBinding.inflate(inflater,container,false)
        return binding.root
    }


}