package com.example.photographycustomer.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.photographycustomer.Fragment.HomeFragment;
import com.example.photographycustomer.Fragment.MyCollectionFragment;
import com.example.photographycustomer.Fragment.PhotoViewFragment;
import com.example.photographycustomer.Fragment.ProfileFragment;
import com.example.photographycustomer.Fragment.SettingFragment;
import com.example.photographycustomer.Fragment.VendorFragment;
import com.example.photographycustomer.Helper.Snackbar;
import com.example.photographycustomer.R;
import com.pixplicity.easyprefs.library.Prefs;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import me.ibrahimsn.lib.NiceBottomBar;

public class MainActivity extends AppCompatActivity {

    private NiceBottomBar bottomBar;
    private long backPressTime;
    private ViewStub viewStub;
    private FrameLayout layout;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottomBarView);
        viewStub = findViewById(R.id.home_error);
        layout = findViewById(R.id.container);

        snackbar = new Snackbar(this);

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            showfragment(new HomeFragment());
            bottomBar.setActiveItem(2);
        }

        bottomBar.setOnItemReselected(integer -> {
            Fragment fragmentSelected = null;
            if (integer == 0){
                fragmentSelected = new MyCollectionFragment();
                bottomBar.setActiveItem(0);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 1){
                snackbar.timeout("This Feature is Under Development");
            }else if (integer == 2){
                fragmentSelected = new HomeFragment();
                bottomBar.setActiveItem(2);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 3){
                fragmentSelected = new PhotoViewFragment();
                bottomBar.setActiveItem(3);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 4){
                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("LOGOUT");
                builder.setMessage("Are you sure want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Prefs.clear();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(MainActivity.this);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();*/

                fragmentSelected = new SettingFragment();
                bottomBar.setActiveItem(4);
                Animatoo.animateFade(MainActivity.this);
            }
            if (fragmentSelected != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragmentSelected);
                ft.addToBackStack(null);
                ft.commit();
            }
            return null;
        });

        bottomBar.setOnItemSelected(integer -> {
            Fragment fragmentSelected = null;
            if (integer == 0){
                fragmentSelected = new MyCollectionFragment();
                bottomBar.setActiveItem(0);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 1){
                snackbar.timeout("This Feature is Under Development");
            }else if (integer == 2){
                fragmentSelected = new HomeFragment();
                bottomBar.setActiveItem(2);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 3){
                fragmentSelected = new PhotoViewFragment();
                bottomBar.setActiveItem(3);
                Animatoo.animateFade(MainActivity.this);
            }else if (integer == 4){
                /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("LOGOUT");
                builder.setMessage("Are you sure want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Prefs.clear();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(MainActivity.this);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();*/
                fragmentSelected = new SettingFragment();
                bottomBar.setActiveItem(4);
                Animatoo.animateFade(MainActivity.this);
            }
            if (fragmentSelected != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragmentSelected);
                ft.addToBackStack(null);
                ft.commit();
            }
            return null;
        });
    }

    private void showfragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof HomeFragment) {
            if (backPressTime + 2000 > System.currentTimeMillis()) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .setTitle("EXIT")
                        .setMessage("Are you Sure that you Want to Exit from APP ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            this.moveTaskToBack(true);
                            this.finish();
                        })
                        .setNegativeButton("No", (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                        })
                        .build();
                materialDialog.show();
            }else {
                //snack.info("Click BACK Again to Exit App");
            }
        }else if (fragment instanceof MyCollectionFragment){
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        }else if (fragment instanceof VendorFragment){
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        }else if (fragment instanceof PhotoViewFragment){
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        }else if (fragment instanceof SettingFragment){
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        }else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                showfragment(new HomeFragment());
                bottomBar.setActiveItem(2);
            }
        }
        backPressTime = System.currentTimeMillis();
    }

}