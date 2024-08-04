package rachman.forniandi.dicodingeventstracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.data.remote.response.EventsItem
import rachman.forniandi.dicodingeventstracker.databinding.ItemEventBinding

class EventsAdapter(private val mContext: Context): RecyclerView.Adapter<EventsAdapter.EventsHolder>() {

    private var events = arrayListOf<EventsItem>()
    private var onClickListener: OnStoryClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsHolder {
        val bindingView = ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventsHolder(bindingView)
    }

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        val eventData = events[position]
        holder.txtTitleEvent.text = eventData.name
        Picasso.get()
            .load(eventData.imageLogo)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(holder.imgPromoteEvent)

    }

    override fun getItemCount(): Int {
        return events.size
    }

    class EventsHolder (view:ItemEventBinding): RecyclerView.ViewHolder(view.root){
        val txtTitleEvent = view.txtTitleNameEvent
        val imgPromoteEvent = view.imgEvent
    }

    interface OnStoryClickListener {
        fun onClick(position: Int, event: EventsItem)
    }
}


