package com.manan.dev.ec2018app.DatabaseHandler;

import android.provider.BaseColumns;

/**
 * Created by nisha on 2/16/2018.
 */

public class Schema {
    public Schema() {
    }
    public static class DbEntry implements BaseColumns {
        public static final String EVENT_LIST_TABLE_NAME="ECevents";
        public static final String EVENT_ID_COLUMN_NAME="EventId";
        public static final String EVENT_NAME_COLUMN_NAME="eventName";
        public static final String EVENT_CLUB_COLUMN_NAME="club";
        public static final String EVENT_CATEGORY_COLUMN_NAME="category";
        public static final String EVENT_DESCRIPTION_COLUMN_NAME="description";
        public static final String EVENT_RULES_COLUMN_NAME="rules";
        public static final String EVENT_VENUE_COLUMN_NAME="venue";
        public static final String EVENT_FEE_COLUMN_NAME="fee";
        public static final String EVENT_START_TIME_COLUMN_NAME="startTime";
        public static final String EVENT_END_TIME_COLUMN_NAME="endTime";
        public static final String EVENT_COORDINATOR_NAME_1="coordinatorName1";
        public static final String EVENT_COORDINATOR_PHONE_1 = "coordinatorPhone1";
        public static final String EVENT_COORDINATOR_ID_1 = "coordinatorId1";
        public static final String EVENT_COORDINATOR_NAME_2="coordinatorName2";
        public static final String EVENT_COORDINATOR_PHONE_2 = "coordinatorPhone2";
        public static final String EVENT_COORDINATOR_ID_2 = "coordinatorId2";
        public static final String EVENT_PHOTO = "photoUrl";
        public static final String EVENT_PRIZES_1 = "prize1";
        public static final String EVENT_PRIZES_2 = "prize2";
        public static final String EVENT_PRIZES_3 = "prize3";
        public static final String EVENT_TEAM_SIZE = "teamSize";
        public static final String EVENT_UNIQUE_ID = "uniqueKey";
        public static final String QR_TICKET_TABLE_NAME = "QRTickets";
        public static final String EVENT_ID = "eventId";
        public static final String QR_CODE = "qrCode";
        public static final String  PAYMENT_STATUS = "paymentStatus";
        public static final String ARRIVAL_STATUS = "arrivalStatus";

    }
}
