package cl.gerardomascayano.tdmadmin

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import cl.gerardomascayano.tdmadmin.core.extension.gone
import cl.gerardomascayano.tdmadmin.core.extension.hideKeyboard
import cl.gerardomascayano.tdmadmin.core.extension.showKeyboard
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.AnimationType
import cl.gerardomascayano.tdmadmin.core.ui.IconLeftTypeActivity
import cl.gerardomascayano.tdmadmin.core.ui.IconRightTypeActivity
import cl.gerardomascayano.tdmadmin.databinding.ActivityMainBinding
import cl.gerardomascayano.tdmadmin.ui.orders.detail.DetailOrderFragment
import cl.gerardomascayano.tdmadmin.ui.orders.list.OrdersFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, TextView.OnEditorActionListener {

    lateinit var binding: ActivityMainBinding
    private var leftIconState = IconLeftTypeActivity.HAMBURGUER
    private var rightIconState = IconRightTypeActivity.SEARCH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMainInclude.toolbar)
        binding.navView.setCheckedItem(R.id.menu_orders)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.appBarMainInclude.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.appBarMainInclude.etSearchOrder.setOnEditorActionListener(this)
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
                IconRightTypeActivity.NOTE -> buttonNotePressed()
                IconRightTypeActivity.SEARCH -> buttonSearchPressed()
                IconRightTypeActivity.CLOSE -> buttonClosePressed(true)
            }
        }
    }

    private fun buttonClosePressed(pressedByUser: Boolean) {
        manageRightIconActivity(IconRightTypeActivity.SEARCH)
        binding.appBarMainInclude.tvTitle.visible()
        binding.appBarMainInclude.etSearchOrder.gone()
        binding.appBarMainInclude.etSearchOrder.hideKeyboard()
        if (pressedByUser) {
            binding.appBarMainInclude.etSearchOrder.setText("")
            executeOrdersFilter(binding.appBarMainInclude.etSearchOrder.text.toString())
        }
    }

    private fun buttonSearchPressed() {
        manageRightIconActivity(IconRightTypeActivity.CLOSE)
        binding.appBarMainInclude.tvTitle.gone()
        binding.appBarMainInclude.etSearchOrder.visible()
        binding.appBarMainInclude.etSearchOrder.showKeyboard()
    }

    private fun buttonNotePressed() {
        val currentFragment = supportFragmentManager.findFragmentByTag(DetailOrderFragment::class.java.simpleName) as DetailOrderFragment
        currentFragment.showOrderNotesDialog()
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

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true, animate: AnimationType? = null) {
        if (rightIconState == IconRightTypeActivity.CLOSE) {
            manageRightIconActivity(IconRightTypeActivity.SEARCH)
            buttonClosePressed(false)
        }
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        if (animate != null) {
            when (animate) {
                AnimationType.FADE -> TODO()
                AnimationType.SLIDE -> transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
        }

        transaction
            .replace(R.id.host_fragment, fragment, fragment::class.java.simpleName)
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
            binding.appBarMainInclude.ibRightAction.setImageDrawable(
                if (rightIconState.drwRes != null) ContextCompat.getDrawable(
                    this,
                    iconRightIcon.drwRes!!
                ) else null
            )
        }
    }

    private fun executeOrdersFilter(filterText: String) {
        val orderFragment = supportFragmentManager.findFragmentByTag(OrdersFragment::class.java.simpleName) as OrdersFragment
        orderFragment.filterOrders(filterText)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayFragment(item.itemId)
        return true
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // Ejecutar búsqueda
            executeOrdersFilter(v?.text.toString())
            binding.appBarMainInclude.etSearchOrder.hideKeyboard()
            return true
        }

        return false
    }

}