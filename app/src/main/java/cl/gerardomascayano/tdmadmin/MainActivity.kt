package cl.gerardomascayano.tdmadmin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import cl.gerardomascayano.tdmadmin.databinding.ActivityMainBinding
import cl.gerardomascayano.tdmadmin.ui.orders.OrdersFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMainInclude.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        setupNavDrawer()
        defaultScreen()
    }

    private fun defaultScreen() {
        displayFragment(R.id.menu_orders)
    }

    private fun setupNavDrawer() {
        val actionBarToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMainInclude.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        actionBarToggle.syncState()
    }


    private fun displayFragment(menuId: Int) {

        val fragment: Fragment? = when (menuId) {
            R.id.menu_orders -> OrdersFragment.newInstance()
            else -> null
        }

        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.host_fragment, it)
                .commit()

            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START, true)
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragment(item.itemId)
        return true
    }

}