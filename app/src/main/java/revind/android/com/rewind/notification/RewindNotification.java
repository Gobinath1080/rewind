package revind.android.com.rewind.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.lang.ref.WeakReference;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteActionCompat;
import androidx.core.app.RemoteInput;
import revind.android.com.rewind.service.RewindService;

public class RewindNotification {

    public static final String ID_CHANNEL = "channel1";
    public static final int ID_BASE_NOTIFICATION = 100;
    public static final String MESSAGE_BIG = "Static library support version of the framework's FragmentTransaction. Used to write apps that run on platforms prior to Android 3.0. When running on Android 3.0 or above, this implementation is still used; it does not try to switch to the framework's implementation. See the framework SDK documentation for a class overview.";
    private NotificationManager notificationManager;

    private NotificationCompat.Builder notificationBuilder;

    private WeakReference<Context> contextWeakReference;

    public static void rewind(Context context){
        RewindNotification rewindNotification = new RewindNotification(context);
        rewindNotification.addPublicVersion();
        rewindNotification.createNotificationChannel();
        rewindNotification.showSimpleNotification();
        rewindNotification.sleep(2000);
        rewindNotification.updatedSimpleNotification();
        rewindNotification.addActions();
        rewindNotification.sleep(2000);
        rewindNotification.addMessageStyle();
    }

    public RewindNotification(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.notificationBuilder = new NotificationCompat.Builder(context, ID_CHANNEL);
        this.notificationBuilder.setCategory(Notification.CATEGORY_RECOMMENDATION);
        this.notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel = new NotificationChannel(ID_CHANNEL,"Channel1",NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
    }

    public void addPublicVersion(){
        NotificationCompat.Builder publicVersionBuilder = new NotificationCompat.Builder(contextWeakReference.get(),ID_CHANNEL);
        publicVersionBuilder.setContentTitle("Public Version");
        publicVersionBuilder.setContentInfo("Public Content");
        notificationBuilder.setPublicVersion(publicVersionBuilder.build());
    }

    public void showSimpleNotification(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(contextWeakReference.get(),0,intent,0);
        notificationBuilder.setSmallIcon(androidx.core.R.drawable.notification_icon_background)
                .setContentTitle("RewindNotification")
                .setContentText("Notification Content for the simple notification")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(ID_BASE_NOTIFICATION,notificationBuilder.build());
    }

    public void updatedSimpleNotification(){
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(MESSAGE_BIG + MESSAGE_BIG));
        notificationManager.notify(ID_BASE_NOTIFICATION,notificationBuilder.build());
    }

    public void addActions(){
        Intent intent = new Intent(contextWeakReference.get(),RewindService.class);
        PendingIntent pendingIntent = PendingIntent.getService(contextWeakReference.get(),0,intent,0);
        RemoteInput remoteInput = new RemoteInput.Builder("input_result").setLabel("Enter you name").build();
        NotificationCompat.Action action  = new NotificationCompat.Action.Builder(androidx.core.R.drawable.notification_icon_background,"Action",pendingIntent).addRemoteInput(remoteInput).build();
        notificationBuilder.addAction(androidx.core.R.drawable.notification_icon_background,"Action2",pendingIntent);
        notificationBuilder.addAction(action);
        notificationManager.notify(ID_BASE_NOTIFICATION,notificationBuilder.build());
    }

    public void addMessageStyle(){
        notificationBuilder.setStyle(new NotificationCompat.MessagingStyle("ME").setConversationTitle("Rewind Android").addMessage("HI",1111,"GObi").addMessage("How are you",22222,"Gokul"));
        notificationManager.notify(ID_BASE_NOTIFICATION,notificationBuilder.build());

    }

    public void sleep(long timeInMilli){
        try {
            Thread.sleep(timeInMilli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
