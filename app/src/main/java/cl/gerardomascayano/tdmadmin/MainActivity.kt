package cl.gerardomascayano.tdmadmin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.IconLeftTypeActivity
import cl.gerardomascayano.tdmadmin.core.ui.IconRightTypeActivity
import cl.gerardomascayano.tdmadmin.databinding.ActivityMainBinding
import cl.gerardomascayano.tdmadmin.ui.orders.OrdersFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var leftIconState = IconLeftTypeActivity.HAMBURGUER
    private var rightIconState = IconRightTypeActivity.NONE

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
        binding.appBarMainInclude.ibLeftAction.setOnClickListener {
            when (leftIconState) {
                IconLeftTypeActivity.HAMBURGUER -> binding.drawerLayout.openDrawer(GravityCompat.START)
                IconLeftTypeActivity.ARROW_BACK -> onBackPressed()
            }
        }

        binding.appBarMainInclude.ibRightAction.setOnClickListener {
            when (rightIconState) {
                IconRightTypeActivity.NONE -> Unit
                IconRightTypeActivity.NOTE -> {
                    //TODO Mostrar dialog ordenes note's
                }
            }
        }
    }

    private fun listeningFragmentStack() {
        supportFragmentManager.addOnBackStackChangedListener {
            val contract = supportFragmentManager.findFragmentById(R.id.host_fragment) as ActivityFragmentContract
            manageLeftIconActivity(contract.iconLeftToShow())
            manageRightIconActivity(contract.iconRightToShow())
        }
    }

    fun updateTitle(title: String) {
        binding.appBarMainInclude.tvTitle.text = title
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

    private fun manageLeftIconActivity(iconLeftType: IconLeftTypeActivity) {
        if (this.leftIconState != iconLeftType) {
            this.leftIconState = iconLeftType
            binding.appBarMainInclude.ibLeftAction.setImageDrawable(ContextCompat.getDrawable(this, iconLeftType.drwRes))
        }
    }

    private fun manageRightIconActivity(iconRightIcon: IconRightTypeActivity) {
        if (this.rightIconState != iconRightIcon) {
            this.rightIconState = iconRightIcon
            binding.appBarMainInclude.ibRightAction.setImageDrawable(ContextCompat.getDrawable(this, iconRightIcon.drwRes!!))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragment(item.itemId)
        return true
    }

}