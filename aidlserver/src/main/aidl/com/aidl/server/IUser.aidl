// IUser.aidl
package com.aidl.server;
import com.aidl.server.User;
// Declare any non-default types here with import statements

interface IUser {

    String getName();
    User getUser(in String name,in String age);

}
