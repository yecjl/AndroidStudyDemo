package com.study.smsreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * 功能：
 * Created by danke on 2017/3/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String address = message.getOriginatingAddress();
            String body = message.getMessageBody();
            Log.d("SmsBroadcastReceiver", "address: " + address + ", body: " + body);
            abortBroadcast();
        }
    }


//    @Override
//    public void onReceive(Context context, Intent intent) {
//        SmsMessage[] smsMessages = getMessagesFromIntent(intent);
//        for (int i = 0; i < smsMessages.length; i++) {
//            SmsMessage smsMessage = smsMessages[i];
//            Log.d("SmsBroadcastReceiver", "getOriginatingAddress: " + smsMessage.getOriginatingAddress());
//            Log.d("SmsBroadcastReceiver", "getMessageBody: " + smsMessage.getMessageBody());
//        }
//    }
//
//    /**
//     * Read the PDUs out of an {@link } or a
//     * {@link } intent.
//     *
//     * @param intent the intent to read from
//     * @return an array of SmsMessages for the PDUs
//     */
//    public static final SmsMessage[] getMessagesFromIntent(
//            Intent intent) {
//        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
//        byte[][] pduObjs = new byte[messages.length][];
//
//        for (int i = 0; i < messages.length; i++) {
//            pduObjs[i] = (byte[]) messages[i];
//        }
//        byte[][] pdus = new byte[pduObjs.length][];
//        int pduCount = pdus.length;
//        SmsMessage[] msgs = new SmsMessage[pduCount];
//        for (int i = 0; i < pduCount; i++) {
//            pdus[i] = pduObjs[i];
//            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
//        }
//        return msgs;
//    }

}
