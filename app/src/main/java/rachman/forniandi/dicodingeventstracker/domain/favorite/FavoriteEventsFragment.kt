package rachman.forniandi.dicodingeventstracker.domain.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.databinding.FragmentFavoriteEventsBinding
import rachman.forniandi.dicodingeventstracker.databinding.FragmentHomeBinding


class FavoriteEventsFragment : Fragment() {

    private var _binding: FragmentFavoriteEventsBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_events, container, false)
    }


}