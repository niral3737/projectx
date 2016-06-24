package projectx.itgo.com;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import projectx.itgo.com.activities.ActivityLogin;
import projectx.itgo.com.database.DBHelper;
import projectx.itgo.com.fragments.FragmentCustomer;
import projectx.itgo.com.fragments.FragmentHome;

public class ActivityMain extends AppCompatActivity {
    private Toolbar toolbar;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.customers_toolbar);
        setSupportActionBar(toolbar);

        final IProfile profile = new ProfileDrawerItem().withName("Kalpesh Patel").withEmail("ambika@gmail.com").withIcon(R.drawable.profile);
        assert toolbar!=null;
        toolbar.setTitle(R.string.drawer_item_home);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header_background)
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_customers).withIcon(FontAwesome.Icon.faw_users),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_signout).withIcon(FontAwesome.Icon.faw_sign_out)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        Fragment fragment = null;
                        Class fragmentClass = FragmentHome.class;
                        if (drawerItem != null) {
                            if (position == 1) {
                                fragmentClass = FragmentHome.class;
                                toolbar.setTitle(R.string.drawer_item_home);
                            } else if (position == 2) {
                                fragmentClass = FragmentCustomer.class;
                                toolbar.setTitle(R.string.drawer_item_customers);
                            } else if(position == 4){
                                DBHelper dbHelper = new DBHelper(ActivityMain.this);
                                dbHelper.deleteUser();
                                Intent intent = new Intent(ActivityMain.this, ActivityLogin.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frame_layout, new FragmentHome()).commit();
    }
}
