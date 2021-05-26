package com.emon.haziraKhata.home

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.emon.haziraKhata.AboutActivity
import com.emon.haziraKhata.Firebase.FirebaseCaller
import com.emon.haziraKhata.HelperClasses.UtilsCommon
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity
import com.emon.haziraKhata.Login.LoginActivity
import com.emon.haziraKhata.Model.JobItems
import com.emon.haziraKhata.R
import com.emon.haziraKhata.Tabs.*
import com.emon.haziraKhata.Tabs.Fragments.TextBookFragment
import com.emon.haziraKhata.routine.RoutineItem
import com.emon.haziraKhata.routine.RoutineUtils
import com.emon.haziraKhata.routine.RoutineViewModel
import com.emon.haziraKhata.service.GenericEventShowingService
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.onesignal.OneSignal
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mProfileHeader: String
    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var drawer: DrawerLayout
    private lateinit var routineViewModel: RoutineViewModel

    private fun setUpDrawer() {

        drawer = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null
        FirebaseCaller.getFirebaseDatabase().keepSynced(true)
        if (FirebaseCaller.getCurrentUser() != null) {
            if (UtilsCommon.isValideString(FirebaseCaller.getCurrentUser().email)) mProfileHeader = FirebaseCaller.getCurrentUser().email.toString() else if (UtilsCommon.isValideString(FirebaseCaller.getCurrentUser().phoneNumber)) mProfileHeader = FirebaseCaller.getCurrentUser().phoneNumber.toString()
        } else {
            mProfileHeader = "এখনো একাউন্ট খুলেননি।খুলতে এখানে ক্লিক করুন"
        }
        val headerView = navigationView.getHeaderView(0)
        val navHeader = headerView.findViewById<RelativeLayout>(R.id.user_pro_pic)
        val emailText = navHeader.findViewById<TextView>(R.id.user_email)
        emailText.text = mProfileHeader
        navHeader.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.putExtra("FLAG", "INSIDE")
            startActivity(intent)
            val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
            drawer.closeDrawer(GravityCompat.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        //If activity is launched from routine service
        if (intent != null && intent.extras != null && intent.extras!!.getParcelableArrayList<Parcelable>(GenericEventShowingService.TRIGGERED_ROUTINES) != null) {
            mainViewModel.triggeredRoutines = intent
                    .extras!!
                    .getParcelableArrayList(GenericEventShowingService.TRIGGERED_ROUTINES)
        }
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(true).init()
        setupRoutine()
        setUpDrawer()
        setupViewPager()
        setupTabIcons()
    }

    private fun setupRoutine() {
        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        if (UtilsCommon.isRoutineNotificationEnable(this)) {
            routineViewModel.allLiveRoutines.observe(this, Observer { routineItems: List<RoutineItem?>? -> RoutineUtils.startEventShowingService(this, routineItems) })
        }
    }

    override fun onResume() {
        super.onResume()
        val authListener = AuthStateListener { firebaseAuth: FirebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
        }
        FirebaseCaller.getAuth().addAuthStateListener(authListener)
        super.onResume()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.privacy_policy -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1Yj7XyuCfGIkJ5S0Qqi0f9Xo1z6a0j19uVP_n0k_tnCE/edit?usp=sharing")))
            R.id.instruction -> UtilsCommon.openWithFaceBook("https://www.facebook.com/notes/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%B6%E0%A6%BF%E0%A6%95%E0%A7%8D%E0%A6%B7%E0%A6%95-%E0%A6%B8%E0%A6%BE%E0%A6%AA%E0%A7%8B%E0%A6%B0%E0%A7%8D%E0%A6%9F-%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%89%E0%A6%A8%E0%A6%BF%E0%A6%9F%E0%A6%BF/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%8F%E0%A6%AA%E0%A7%87%E0%A6%B0-%E0%A6%AC%E0%A7%8D%E0%A6%AF%E0%A6%AC%E0%A6%B9%E0%A6%BE%E0%A6%B0%E0%A6%AC%E0%A6%BF%E0%A6%A7%E0%A6%BF/2045598845687496/", this)
            R.id.questuon_answer -> UtilsCommon.openWithFaceBook(
                    "https://www.facebook.com/groups/2035798976667483/permalink/2045734145673966/", this)
            R.id.review -> {
                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setMessage("এপটি এখনো ডেভেলপিং দশায় রয়েছে । " +
                        "সুতরাং শিক্ষক হিসাবে আপনার মূল্যবান মতামত প্রদান করে ডেভেলপারকে সাহায্য করুন।")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "মতামত দিন"
                ) { dialog: DialogInterface?, which: Int ->
                    UtilsCommon.openInAppBrowser(
                            "https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/",
                            this@MainActivity)
                }
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "বাদ দিন") { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
            R.id.setting -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.share_the_app -> {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.putExtra(Intent.EXTRA_TEXT, "#হাজিরা_খাতা \n ( বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ )\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk")
                i.type = "text/plain"
                startActivity(Intent.createChooser(i, "শেয়ার করুন।"))
            }
            R.id.rating -> {
                val appPackageNameRating = packageName // getPackageName() fromTime Context or Activity object
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageNameRating")))
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageNameRating")))
                }
            }
            R.id.facebook_community_Title -> UtilsCommon.openWithFaceBook("https://www.facebook.com/groups/2035798976667483/permalink/2045342365713144/", this)
            R.id.about -> {
                val intent1 = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent1)
            }
        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    fun setupViewPager() {
        viewPager = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(ClassRoomFragment(), "শ্রেণী কার্যক্রম")
        adapter.addFrag(NibondhonFragment(), "শিক্ষক নিবন্ধন কর্নার")
        adapter.addFrag(TotthojhuriFragment(), "তথ্য ঝুড়ি")
        adapter.addFrag(JobFragment(), "শিক্ষক নিয়োগ")
        adapter.addFrag(BlogFragment(), "শিক্ষক কথন")
        adapter.addFrag(TextBookFragment(), "পাঠ্যবই")
        viewPager.setAdapter(adapter)
        viewPager.setOffscreenPageLimit(adapter.count)
    }

    private fun setupTabIcons() {
        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = " শ্রেণী কার্যক্রম"
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_class_fragment, 0, 0, 0)
        tabLayout!!.getTabAt(0)!!.customView = tabOne
        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = " শিক্ষক নিবন্ধন কর্নার"
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_nibondhon, 0, 0, 0)
        tabLayout!!.getTabAt(1)!!.customView = tabTwo
        val tabFour = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabFour.text = " তথ্য ঝুড়ি"
        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_tottho, 0, 0, 0)
        tabLayout!!.getTabAt(2)!!.customView = tabFour
        val tabFive = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabFive.text = " শিক্ষক নিয়োগ"
        tabFive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_job, 0, 0, 0)
        tabLayout!!.getTabAt(3)!!.customView = tabFive
        val tabSix = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabSix.text = " শিক্ষক কথন"
        tabSix.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_shikkhok_kothon, 0, 0, 0)
        tabLayout!!.getTabAt(4)!!.customView = tabSix
        val tabSeven = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabSeven.text = " পাঠ্যবই"
        tabSeven.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_text_book, 0, 0, 0)
        tabLayout!!.getTabAt(5)!!.customView = tabSeven
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    companion object {
        @JvmField
        var Job_list: ArrayList<JobItems>? = null
    }
}