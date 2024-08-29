package rachman.forniandi.dicodingeventstracker.domain.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.databinding.FragmentPastEventsBinding
import rachman.forniandi.dicodingeventstracker.databinding.FragmentSearchEventBinding


class SearchEventFragment : Fragment() {


    private var _binding: FragmentSearchEventBinding?= null
    private val binding get() = _binding!!
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val activeValue:Int=-1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchEventBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("TAG", "Ini adalah halaman searchEventFragment")
    }


}