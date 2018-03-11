package com.manan.dev.ec2018app.DatabaseHandler;

/**
 * Created by nisha on 2/16/2018.
 */
//2604
public class dbUtils {
    private static final String COMMA_SEP = ",";
    public static final String CREATE_TABLE = "CREATE TABLE " + Schema.DbEntry.EVENT_LIST_TABLE_NAME + "( "
            + Schema.DbEntry.EVENT_ID_COLUMN_NAME + " TEXT PRIMARY KEY" + COMMA_SEP
            + Schema.DbEntry.EVENT_NAME_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_CLUB_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_RULES_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_VENUE_COLUMN_NAME + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_FEE_COLUMN_NAME + " NUMBER" + COMMA_SEP
            + Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME + " NUMBER" + COMMA_SEP
            + Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME + " NUMBER" + COMMA_SEP
            + Schema.DbEntry.EVENT_PHOTO + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_TEAM_SIZE + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_PRIZES_1 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_PRIZES_2 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_PRIZES_3 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_ID_1 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_NAME_1 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_PHONE_1 + " NUMBER" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_ID_2 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_NAME_2 + " TEXT" + COMMA_SEP
            + Schema.DbEntry.EVENT_COORDINATOR_PHONE_2 + " NUMBER" +")";
}
