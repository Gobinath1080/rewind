package revind.android.com.rewind.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import revind.android.com.rewind.IRewindIPC;
import revind.android.com.rewind.model.Item;
import revind.android.com.rewind.model.User;


public class RewindService extends Service {

    public static final String TAG = RewindService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Running");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Running");
        return super.onStartCommand(intent, flags, startId);
    }

    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Running");
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Running");
        super.onDestroy();
    }

    IRewindIPC.Stub binder = new IRewindIPC.Stub() {
        @Override
        public String getServiceName() throws RemoteException {
            return TAG;
        }

        @Override
        public Item getItem(int index) throws RemoteException {
            return new Item("Flower",100);
        }

        @Override
        public User getUser() throws RemoteException {
            return new User("ChatBot");
        }
    };
}
