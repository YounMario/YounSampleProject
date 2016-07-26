package com.younchen.younsampleproject.sys.pic;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.younchen.younsampleproject.IPersonAidlInterface;
import com.younchen.younsampleproject.sys.pic.bean.Person;
import com.younchen.younsampleproject.sys.pic.listener.PersonNotifiyListener;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class AidlCallBackService extends Service {


    private PersonManager personManager;
    private Thread mThread;
    private AtomicBoolean atomicBoolean;

    private final String TAG = "AidlCallBackService";

    @Override
    public void onCreate() {
        super.onCreate();
        personManager = new PersonManager();
        atomicBoolean = new AtomicBoolean(true);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (atomicBoolean.get()) {
                    count++;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Person person = new Person(count, "龙泉" + count);
                        personManager.putMan(person);
                        personManager.notifyDataChanged(person);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }

    public AidlCallBackService() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        atomicBoolean.set(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return personManager.asBinder();
    }

    private class PersonManager extends IPersonAidlInterface.Stub {

        private ConcurrentHashMap<Integer, Person> personMap;
        //代替list
        private RemoteCallbackList<PersonNotifiyListener> listenerQueue;

        public PersonManager() {
            personMap = new ConcurrentHashMap<>();

            listenerQueue = new RemoteCallbackList<>();
        }

        @Override
        public Person getMan(int id) throws RemoteException {
            return personMap.get(id);
        }

        @Override
        public void putMan(Person man) throws RemoteException {
            if (man != null) {
                personMap.put(man.getId(), man);
            }
        }

        @Override
        public void registListenter(PersonNotifiyListener listener) throws RemoteException {
            if (listener != null) {
                listenerQueue.register(listener);
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unRegistListener(PersonNotifiyListener listener) throws RemoteException {
            if (listener != null) {
                Log.i(TAG, "before remove list size:" + listenerQueue.getRegisteredCallbackCount());
                listenerQueue.unregister(listener);
                Log.i(TAG, "after remove list size:" + listenerQueue.getRegisteredCallbackCount());
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void notifyDataChanged(Person person) throws RemoteException {
            int count = listenerQueue.beginBroadcast();
            for (int i = 0; i < count; i++) {
                PersonNotifiyListener l = listenerQueue.getBroadcastItem(i);
                if (l != null) {
                    l.onPersonAdded(person);
                }
            }
            listenerQueue.finishBroadcast();
        }
    }
}
