package com.kartala.poc;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag = true;
    FirebaseMessaging firebaseMessaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        //Tambah home fragment di dequeList
        integerDeque.push(R.id.bnHome);
        //Muat home fragment
        loadFragment(new HomeFragment());
        //Jadikan home fragment sebagai default
        bottomNavigationView.setSelectedItemId(R.id.bnHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Ambil item id yang diselect
                int id = item.getItemId();
                if(integerDeque.contains(id)){
                    //Ketika list deque memiliki selected id
                    //Cek kondisi
                    if(id == R.id.bnHome){
                        //Ketika selected id = home fragment id
                        //Cek kondisi
                        if(integerDeque.size()!=1){
                            //Ketika list deque != 1
                            //Cek kondisi
                            if(flag){
                                //Ketika flag = true
                                //Tambah home fragment di deque list
                                integerDeque.addFirst(R.id.bnHome);
                                flag=false;

                            }
                        }
                    }
                    //Hapus selected id dari list deque
                    integerDeque.remove(id);
                }
                //Push selected id di list deque
                integerDeque.push(id);
                //Muat fragment
                loadFragment(getFragment(item.getItemId()));
                //return true
                return false;
            }
        });
    }


    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.bnHome:
                //Set home fragment yang di cek
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                //Kembali ke home fragment
                return new HomeFragment();
            case R.id.bnList:
                //Set create fragment yang di cek
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                //Kembali ke create fragment
                return new ListFragment();
            case R.id.bnProfile:
                //Set list fragment yang di cek
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                //Kembali ke list fragment
                return new ProfileFragment();
        }
        //Set checked default home fragment
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,fragment,fragment.getClass().getSimpleName())
                .commit();
    }

//    @Override
//    public void onBackPressed() {
//        //Pop fragment sebelumnya
//        integerDeque.pop();
//        if(!integerDeque.isEmpty()){
//            //Ketika list deque tidak kosong
//            //Muat Fragment
//            loadFragment(getFragment(integerDeque.peek()));
//        }else{
//            //Ketika list deque kosong
//            //Finish activity
//            finish();
//        }
//    }

    public void onBackPressed() {
    }
}
