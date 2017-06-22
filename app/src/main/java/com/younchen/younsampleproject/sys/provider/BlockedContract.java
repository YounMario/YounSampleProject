package com.younchen.younsampleproject.sys.provider;

import android.net.Uri;

/**
 * Created by Administrator on 2017/6/22.
 */

public class BlockedContract {

    public static Uri AUTHORITY_URI = Uri.parse("content://" + BlockedContactProviderHelper.getAuthorities());

    public interface BlockedContactColumns {

        static final String _ID = "_id";

        static final String CONTACT_ID = "contact_id";

        static final String CONTACT_LOOK_UP_KEY = "look_up_key";

        static final String COUNTRY_ISO = "country_iso";

        static final String PHONE_NUMBER = "phone_number";

        static final String CREATED_TIME = "created_time";

    }

    public static class BlockedContacts {

        public static final String TABLE_NAME = "blocked_contact";

        public static final Uri BlockedContactUri = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/blocked_contact";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/blocked_contact";

    }

}
