package com.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aidl.server.IConnect;
import com.aidl.server.IUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IConnect iConnect;
    private IUser iUser;
    Intent connect;
    Intent connect1;

    Intent userintent;
    Intent userintent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aidlconnect();
        aidluser();
    }

    private void aidluser() {
        userintent1 = new Intent();
        userintent1.setAction("com.aidl.server.user");
        //intent.setPackage("com.aidl.server");
        userintent = new Intent(createExplicitFromImplicitIntent(this,userintent1));
        bindService(userintent,connuser,BIND_AUTO_CREATE);
    }

    private void aidlconnect() {
        connect1 = new Intent();
        connect1.setAction("com.aidl.server.connect");
        //intent.setPackage("com.aidl.server");
        connect = new Intent(createExplicitFromImplicitIntent(this,connect1)) ;
        bindService(connect,conn,BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            iConnect = IConnect.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public void test(View v){
        try {
            Toast.makeText(this,iConnect.connect(),Toast.LENGTH_LONG).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loaduser(View v){
        try {
            Toast.makeText(this,iUser.getUser("tony","20").toString(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ServiceConnection connuser = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            iUser = IUser.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        unbindService(connuser);
    }

}
