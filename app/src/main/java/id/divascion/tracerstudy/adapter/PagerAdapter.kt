package id.divascion.tracerstudy.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.divascion.tracerstudy.data.model.AlumniQuiz

class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun addFragment(fragment: Fragment, title: String, key: String, value: AlumniQuiz) {
        fragments.add(fragment)
        val bundle = Bundle()
        bundle.putParcelable(key, value)
        bundle.putString("STATUS", "done")
        fragment.arguments = bundle
        titles.add(title)
    }
}