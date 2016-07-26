// PersonNotifiyListener.aidl
package com.younchen.younsampleproject.sys.pic.listener;
import com.younchen.younsampleproject.sys.pic.bean.Person;
// Declare any non-default types here with import statements
interface PersonNotifiyListener{
    void onPersonAdded(in Person person);
}