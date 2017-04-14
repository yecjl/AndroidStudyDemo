package com.study.readcontacts.Util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：系统联系人的操作表
 * Created by danke on 2017/4/11.
 */

public class ContactsInfoUtils {
    private static Map<String, String> mKeyValues = new HashMap<>();

    static {
        mKeyValues.put("NAME", "vnd.android.cursor.item/name");
        mKeyValues.put("PHONE", "vnd.android.cursor.item/phone_v2");
        mKeyValues.put("EMAIL", "vnd.android.cursor.item/email_v2");
    }

    public static Uri rcUri = Uri.parse("content://com.android.contacts/raw_contacts"); // 联系人raw_contacts表
    public static Uri dataUri = Uri.parse("content://com.android.contacts/data"); // 联系人data表（其实是view_data视图表）

    /**
     * 获取所有的联系人
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getAllContactsInfo(Context context) {
        List<ContactInfo> mList = new ArrayList<>();

        // 获取内容提供者
        ContentResolver resolver = context.getContentResolver();

        // 先查询raw_contacts表，获取联系人的id
        Cursor rcCursor = resolver.query(rcUri, new String[]{"contact_id"}, null, null, null);
        if (rcCursor != null) {
            while (rcCursor.moveToNext()) {
                String contactId = rcCursor.getString(0);

                // 判断contact_id是否为空
                if (!TextUtils.isEmpty(contactId)) {
                    ContactInfo contactInfo = new ContactInfo();
                    // 查询数据内容
                    Cursor dataCursor = resolver.query(dataUri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contactId}, null);
                    if (dataCursor != null) {
                        while (dataCursor.moveToNext()) {
                            String data1 = dataCursor.getString(0);
                            String mimetype = dataCursor.getString(1);

                            // 查询数据对应是什么type
                            Log.d("mimetype: ", mimetype);

                            if (mimetype.equals(mKeyValues.get("NAME"))) {
                                contactInfo.setName(data1);
                            } else if (mimetype.equals(mKeyValues.get("PHONE"))) {
                                contactInfo.setPhone(data1);
                            } else if (mimetype.equals(mKeyValues.get("EMAIL"))) {
                                contactInfo.setEmail(data1);
                            }
                        }
                        dataCursor.close();
                    }

                    mList.add(contactInfo);
                }
            }
            rcCursor.close();
        }
        return mList;
    }

    /**
     * 将ContactInfo添加到联系人表中
     *
     * @param context
     * @param contactInfo
     */
    public static void insertContactInfo(Context context, ContactInfo contactInfo) {
        if (contactInfo == null) {
            return;
        }

        // 获取内容提供者
        ContentResolver resolver = context.getContentResolver();

        // 先倒叙查询查询raw_contacts表，获取联系人的id
        Cursor rcCursor = resolver.query(rcUri, new String[]{"_id"}, null, null, "_id desc");
        if (rcCursor != null) {
            rcCursor.moveToNext();
            int id = rcCursor.getInt(0) + 1;
            rcCursor.close();

            // 添加raw_contacts表
            ContentValues rcValues = new ContentValues();
            rcValues.put("contact_id", id);
            resolver.insert(rcUri, rcValues);

            // 添加data表 -- name
            ContentValues nameValues = new ContentValues();
            nameValues.put("raw_contact_id", id);
            nameValues.put("data1", contactInfo.getName());
            nameValues.put("mimetype", mKeyValues.get("NAME"));
            resolver.insert(dataUri, nameValues);

            // 添加data表 -- phone
            ContentValues dataValues = new ContentValues();
            dataValues.put("raw_contact_id", id);
            dataValues.put("data1", contactInfo.getPhone());
            dataValues.put("mimetype", mKeyValues.get("PHONE"));
            resolver.insert(dataUri, dataValues);

            // 添加data表 -- email
            ContentValues emailValues = new ContentValues();
            emailValues.put("raw_contact_id", id);
            emailValues.put("data1", contactInfo.getEmail());
            emailValues.put("mimetype", mKeyValues.get("EMAIL"));
            resolver.insert(dataUri, emailValues);
        }
    }
}
