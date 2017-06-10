package com.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class UserService extends Service {
    public UserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    class MyBinder extends IUser.Stub {

        @Override
        public String getName() throws RemoteException {
            return "tony";
        }

        @Override
        public User getUser(String name, String age) throws RemoteException {
            return new User(name,age);
        }

    }


}
