package b_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import c3_fragment_newfeed.fragmentBusiness
import c3_fragment_newfeed.fragmentEnducation
import c3_fragment_newfeed.fragmentLaw
import c3_fragment_newfeed.fragmentSocial
import c3_fragment_newfeed.fragmentSport
import c3_fragment_newfeed.fragmentWorld

class adpaterViewPager(val fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 ->{fragmentSocial()}
            1 ->{fragmentSport()}
            2 ->{fragmentEnducation()}
            3 ->{fragmentLaw()}
            4->{fragmentBusiness()}
            else -> { fragmentWorld()}
        }
    }
    }
