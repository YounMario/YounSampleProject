// IPersonAidlInterface.aidl
package com.younchen.younsampleproject;

// Declare any non-default types here with import statements
import com.younchen.younsampleproject.sys.pic.bean.Person;
import com.younchen.younsampleproject.sys.pic.listener.PersonNotifiyListener;

interface IPersonAidlInterface {

    Person getMan(int id);

    void putMan(in Person man);

    void registListenter(in PersonNotifiyListener listener);

    void unRegistListener(in PersonNotifiyListener listener);
}
