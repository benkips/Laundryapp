package com.mamafua.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.mamafua.app.viewmodels.Cartviewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class index : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var cartQuantity:String
    private var cartQuantity:Int = 0
    private  val cartviewmodel by viewModels<Cartviewmodel>()
    private lateinit var cartBadgeTextView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //get cart

      /*  cartviewmodel.cartLists.observe(this@index){

            cartQuantity=it.size
            //cartBadgeTextView.setText(cartQuantity)
           // Log.d( "index",cartQuantity)
            cartviewmodel.getcartcontentz()
            //Toast.makeText(this, cartQuantity, Toast.LENGTH_SHORT).show()
        }*/



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.Home, R.id.orders, R.id.services, R.id.cartfragment,R.id.mylocation
            ), drawerLayout
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.login) {
                toolbar.visibility = View.GONE
            } else if(destination.id == R.id.registration) {
                toolbar.visibility = View.GONE
            }else if(destination.id == R.id.launcherscreen){
                toolbar.visibility = View.GONE
            } else{
                toolbar.visibility = View.VISIBLE
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.index, menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.index, menu)
        val menuItem = menu.findItem(R.id.cartfragment)
        val actionView = menuItem.actionView

        cartBadgeTextView = actionView.findViewById<TextView>(R.id.cart_badge_text_view)
        cartviewmodel.getcartcontentz()
        cartviewmodel.cartLists.observe(this) {
            cartQuantity=it.size
            cartBadgeTextView.setText(cartQuantity.toString())
            cartBadgeTextView.visibility = if (cartQuantity == 0) View.GONE else View.VISIBLE
            cartviewmodel.getcartcontentz()
        }



        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }

        return true
    }

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val navController = findNavController(R.id.nav_host_fragment)
    return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
}
    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}