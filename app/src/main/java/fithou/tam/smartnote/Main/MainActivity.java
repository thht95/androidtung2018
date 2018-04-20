package fithou.tam.smartnote.Main;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fithou.tam.smartnote.Alarm.AlarmActivity;
import fithou.tam.smartnote.CustomizeNote.CustomizeNoteActivity;
import fithou.tam.smartnote.CustomizeNote.ResultActivity;
import fithou.tam.smartnote.Database.SQLiteDataController;
import fithou.tam.smartnote.Group.GroupFragment;
import fithou.tam.smartnote.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    FloatingActionButton fab_note, fab_alarm, fab_notifi;
    boolean onoffFAB = false;
    Animation moveLeft, moveTop, moveTL;
    Animation backleft, backtop, backtopleft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoadAnimations();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        LoadControls();
        fab_note.setOnClickListener(this);
        fab_alarm.setOnClickListener(this);
        fab_notifi.setOnClickListener(this);

        fab.setSelected(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onoffFAB == false) {
                    MoveFAB();
                    onoffFAB = true;
                    fab.setSelected(false);
                } else {
                    MoveBackFAB();
                    onoffFAB = false;
                    fab.setSelected(true);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setTitle("Ghi chú");
        Fragment mainfragment = new MainFragment();
        CallFragment(mainfragment, "Ghi chú");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //cancelNotification(this);
//        SQLiteDataController sqLiteDataController=new SQLiteDataController(this);
//        sqLiteDataController.deleteDatabase();

    }

    private void LoadAnimations() {
        moveLeft = AnimationUtils.loadAnimation(this, R.anim.moveleft);
        moveTop = AnimationUtils.loadAnimation(this, R.anim.movetop);
        moveTL = AnimationUtils.loadAnimation(this, R.anim.movetopleft);
        backleft = AnimationUtils.loadAnimation(this, R.anim.backleft);
        backtop = AnimationUtils.loadAnimation(this, R.anim.backtop);
        backtopleft = AnimationUtils.loadAnimation(this, R.anim.backtopleft);
    }

    private void LoadControls() {
        fab_note = (FloatingActionButton) findViewById(R.id.fab_note);
        fab_alarm = (FloatingActionButton) findViewById(R.id.fab_alarm);
        fab_notifi = (FloatingActionButton) findViewById(R.id.fab_notifi);
    }


    private void MoveFAB() {
        FrameLayout.LayoutParams paramsLeft = (FrameLayout.LayoutParams) fab_alarm.getLayoutParams();
        paramsLeft.rightMargin = (int) (fab_alarm.getWidth() * 1);
        fab_alarm.setLayoutParams(paramsLeft);
        fab_alarm.startAnimation(moveLeft);

        FrameLayout.LayoutParams paramstop = (FrameLayout.LayoutParams) fab_note.getLayoutParams();
        paramstop.bottomMargin = (int) (fab_note.getWidth() * 1.1);
        fab_note.setLayoutParams(paramstop);
        fab_note.startAnimation(moveTop);

        FrameLayout.LayoutParams paramstopleft = (FrameLayout.LayoutParams) fab_notifi.getLayoutParams();
        paramstopleft.bottomMargin = (int) (fab_notifi.getWidth() * 0.8);
        paramstopleft.rightMargin = (int) (fab_notifi.getWidth() * 0.8);
        fab_notifi.setLayoutParams(paramstopleft);
        fab_notifi.startAnimation(moveTL);
    }

    private void MoveBackFAB() {
        FrameLayout.LayoutParams paramsLeft = (FrameLayout.LayoutParams) fab_alarm.getLayoutParams();
        paramsLeft.rightMargin -= (int) (fab_alarm.getWidth() * 1);
        fab_alarm.setLayoutParams(paramsLeft);
        fab_alarm.startAnimation(backleft);

        FrameLayout.LayoutParams paramstop = (FrameLayout.LayoutParams) fab_note.getLayoutParams();
        paramstop.bottomMargin -= (int) (fab_note.getWidth() * 1.1);
        fab_note.setLayoutParams(paramstop);
        fab_note.startAnimation(backtop);

        FrameLayout.LayoutParams paramstopleft = (FrameLayout.LayoutParams) fab_notifi.getLayoutParams();
        paramstopleft.bottomMargin -= (int) (fab_notifi.getWidth() * 0.8);
        paramstopleft.rightMargin -= (int) (fab_notifi.getWidth() * 0.8);
        fab_notifi.setLayoutParams(paramstopleft);
        fab_notifi.startAnimation(backtopleft);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Cài đặt", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CallFragment(Fragment fragment, String title) {
        getSupportActionBar().setTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_all) {
            Fragment fragment = new MainFragment();
            CallFragment(fragment, "Ghi chú");
        } else if (id == R.id.nav_group) {
            Fragment fragment = new GroupFragment();
            CallFragment(fragment, "Nhóm ghi chú");

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_view) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_note:
                Intent note = new Intent(MainActivity.this, CustomizeNoteActivity.class);
                note.putExtra("TypeID", 4);
                note.putExtra("Title","Nhật ký");
                startActivityForResult(note, 40);
                break;
            case R.id.fab_alarm:
                Intent i=new Intent(MainActivity.this, AlarmActivity.class);
                i.putExtra("TypeID",3);
                i.putExtra("Title","Báo thức");
                startActivity(i);
                break;
            case R.id.fab_notifi:
                Intent noti = new Intent(MainActivity.this, CustomizeNoteActivity.class);
                noti.putExtra("TypeID", 1);
                noti.putExtra("Title","Ghi chú");
                startActivityForResult(noti, 10);
                break;
        }
    }
}
