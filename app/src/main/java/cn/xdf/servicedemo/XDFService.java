package cn.xdf.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class XDFService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new XDFBinder();
    }

    private static class XDFBinder extends IXDFAidlInterface.Stub {

        private IXDFPlayerStatusListener mListener;

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e("basicTypes", "int :" + anInt +
                    " long: " + aLong +
                    " boolean: " + aBoolean +
                    " float: " + aFloat +
                    " double: " + aDouble +
                    " String: " + aString);
        }

        @Override
        public void setPause(String pause) throws RemoteException {
            Log.e("setPause", "暂停播放--->" + pause);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        mListener.onPauseSuccess();

                        Thread.sleep(1000);
                        mListener.onPauseFailed(10010);

                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }

        @Override
        public void setPlay(String play) throws RemoteException {
            Log.e("setPlay", "开始播放--->" + play);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        mListener.onPlaySuccess();

                        Thread.sleep(1000);
                        mListener.onPlayFailed(10086);

                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        @Override
        public void setOnXDFPlayerStatusListener(IXDFPlayerStatusListener listener) throws RemoteException {
            mListener = listener;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate", "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand", "Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "Service onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("onUnbind", "Service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("onRebind", "Service onRebind");
        super.onRebind(intent);
    }
}
