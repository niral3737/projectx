package projectx.itgo.com.utilities;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Niral on 09-06-2016.
 */
public class CustomersResultReceiver extends ResultReceiver {
    private Receiver receiver;
    public CustomersResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(this.receiver != null){
            receiver.onReceiveResult(resultCode,resultData);
        }
    }
}
