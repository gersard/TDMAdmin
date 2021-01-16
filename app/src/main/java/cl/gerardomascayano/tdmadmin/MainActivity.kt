package cl.gerardomascayano.tdmadmin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.IconTypeActivity
import cl.gerardomascayano.tdmadmin.databinding.ActivityMainBinding
import cl.gerardomascayano.tdmadmin.ui.orders.OrdersFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var leftIconState = IconTypeActivity.HAMBURGUER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMainInclude.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.appBarMainInclude.toolbar.setNavigationOnClickListener { onBackPressed() }

        listeningFragmentStack()
        listeningNavigationButton()
    }

    private fun listeningNavigationButton() {
        binding.appBarMainInclude.ibIcon.setOnClickListener {
            when (leftIconState) {
                IconTypeActivity.HAMBURGUER -> binding.drawerLayout.openDrawer(GravityCompat.START)
                IconTypeActivity.ARROW_BACK -> onBackPressed()
            }
        }
    }

    private fun listeningFragmentStack() {
        supportFragmentManager.addOnBackStackChangedListener {
            val contract = supportFragmentManager.findFragmentById(R.id.host_fragment) as ActivityFragmentContract
            manageIconActivity(contract.iconLeftToShow())
        }
    }

    fun updateTitle(title: String) {
        binding.appBarMainInclude.tvTitle.text = title
    }

    private fun showBackArrow() {
        binding.appBarMainInclude.ibIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_arrow))
    }

    private fun showHamburguerIcon() {
        binding.appBarMainInclude.ibIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_hamburguer))
    }

    private fun displayFragment(menuId: Int) {

        val fragment: Fragment? = when (menuId) {
            R.id.menu_orders -> OrdersFragment.newInstance()
            else -> null
        }

        fragment?.let {

            replaceFragment(it, false)

            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START, true)
            }
        }

    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.host_fragment, fragment)
            .commit()
    }

    private fun manageIconActivity(iconType: IconTypeActivity) {
        this.leftIconState = iconType
        when (iconType) {
            IconTypeActivity.HAMBURGUER -> showHamburguerIcon()
            IconTypeActivity.ARROW_BACK -> showBackArrow()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragment(item.itemId)
        return true
    }

}