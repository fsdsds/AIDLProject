package com.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ConnectService extends Service {
    public ConnectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    class MyBinder extends IConnect.Stub
    {

        @Override
        public String connect() throws RemoteException {
            return "connect success";
        }
    }

}
