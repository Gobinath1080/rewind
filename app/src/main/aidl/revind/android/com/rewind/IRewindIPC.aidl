// IRewindIPC.aidl
package revind.android.com.rewind;

import revind.android.com.rewind.model.User;

import revind.android.com.rewind.model.Item;



interface IRewindIPC {

   String getServiceName();

   Item getItem(int index);

   User getUser();
}
