package com.manan.dev.ec2018app.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;

import java.util.ArrayList;

/**
 * Created by nisha on 2/16/2018.
 */

public class DatabaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="ElementsCulmyca.db";
    private static final String TAG="DbHelper";
    private Context context;

    public DatabaseController(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }


    public DatabaseController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(dbUtils.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addEntryToDb(EventDetails event){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Schema.DbEntry.EVENT_ID_COLUMN_NAME,event.getmEventId());
        values.put(Schema.DbEntry.EVENT_NAME_COLUMN_NAME,event.getmName());
        values.put(Schema.DbEntry.EVENT_CLUB_COLUMN_NAME,event.getmClubname());
        values.put(Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME,event.getmCategory());
        values.put(Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME,event.getmDesc());
        values.put(Schema.DbEntry.EVENT_RULES_COLUMN_NAME,event.getmRules());
        values.put(Schema.DbEntry.EVENT_VENUE_COLUMN_NAME,event.getmVenue());
        values.put(Schema.DbEntry.EVENT_FEE_COLUMN_NAME,event.getmFees());
        values.put(Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME,event.getmStartTime());
        values.put(Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME,event.getmEndTime());
        values.put(Schema.DbEntry.EVENT_PHOTO, event.getmPhotoUrl());
        values.put(Schema.DbEntry.EVENT_PRIZES, event.getmPrizes());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_1, event.getmCoordinators().get(0).getmCoordId());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_1, event.getmCoordinators().get(0).getmCoordName());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_1, event.getmCoordinators().get(0).getmCoordPhone());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_2, event.getmCoordinators().get(1).getmCoordId());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_2, event.getmCoordinators().get(1).getmCoordName());
        values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_2, event.getmCoordinators().get(1).getmCoordPhone());
        db.insert(Schema.DbEntry.EVENT_LIST_TABLE_NAME,null,values);
    }
    public ArrayList<EventDetails> retreiveCategory(String clubName){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<EventDetails> eventList=new ArrayList<>();
        String[] projection={ Schema.DbEntry.EVENT_ID_COLUMN_NAME,Schema.DbEntry.EVENT_NAME_COLUMN_NAME
                ,Schema.DbEntry.EVENT_CLUB_COLUMN_NAME,Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME
                ,Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME,Schema.DbEntry.EVENT_RULES_COLUMN_NAME
                ,Schema.DbEntry.EVENT_VENUE_COLUMN_NAME,Schema.DbEntry.EVENT_FEE_COLUMN_NAME
                ,Schema.DbEntry.EVENT_PHOTO,Schema.DbEntry.EVENT_COORDINATOR_NAME_1,Schema.DbEntry.EVENT_COORDINATOR_PHONE_1
                ,Schema.DbEntry.EVENT_COORDINATOR_ID_1,Schema.DbEntry.EVENT_COORDINATOR_NAME_2,Schema.DbEntry.EVENT_COORDINATOR_PHONE_2
                ,Schema.DbEntry.EVENT_COORDINATOR_ID_2 ,Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME
                ,Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME,Schema.DbEntry.EVENT_PRIZES};
        Cursor readCursor=db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME,projection,Schema.DbEntry.EVENT_CLUB_COLUMN_NAME+" = ?",new String[]{clubName} ,null,null,null);
        readCursor.moveToFirst();
        int totalEvents=readCursor.getCount();
        while(totalEvents>0){
            totalEvents--;
            EventDetails event = new EventDetails();
            event = retriveEvents(readCursor);
            eventList.add(event);
            readCursor.moveToNext();

        }
        readCursor.close();
        return eventList;
    }
    public  EventDetails retreiveEventsByID(String EventId)
    {
        SQLiteDatabase db=getReadableDatabase();
        EventDetails ev = new EventDetails();
        String[] projection={ Schema.DbEntry.EVENT_ID_COLUMN_NAME,Schema.DbEntry.EVENT_NAME_COLUMN_NAME
                ,Schema.DbEntry.EVENT_CLUB_COLUMN_NAME,Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME
                ,Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME,Schema.DbEntry.EVENT_RULES_COLUMN_NAME
                ,Schema.DbEntry.EVENT_VENUE_COLUMN_NAME,Schema.DbEntry.EVENT_FEE_COLUMN_NAME
                ,Schema.DbEntry.EVENT_PHOTO,Schema.DbEntry.EVENT_COORDINATOR_NAME_1,Schema.DbEntry.EVENT_COORDINATOR_PHONE_1
                ,Schema.DbEntry.EVENT_COORDINATOR_ID_1,Schema.DbEntry.EVENT_COORDINATOR_NAME_2,Schema.DbEntry.EVENT_COORDINATOR_PHONE_2
                ,Schema.DbEntry.EVENT_COORDINATOR_ID_2 ,Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME
                ,Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME,Schema.DbEntry.EVENT_PRIZES};
        Cursor readCursor=db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME,projection,Schema.DbEntry.EVENT_ID_COLUMN_NAME+" = ?",new String[]{EventId} ,null,null,null);
        readCursor.moveToFirst();
        ev = retriveEvents(readCursor);
        readCursor.close();
        return ev;
    }

    private EventDetails retriveEvents(Cursor readCursor) {
        String eventId=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_ID_COLUMN_NAME));
        String eventName=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_NAME_COLUMN_NAME));
        String category=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME));
        String description=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME));
        String rules=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_RULES_COLUMN_NAME));
        String venue=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_VENUE_COLUMN_NAME));
        Long fee=readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_FEE_COLUMN_NAME));
        String prizes=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PRIZES));
        String photourl=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PHOTO));
        Long startTime=readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME));
        String clubName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_CLUB_COLUMN_NAME));
        Long endTime=readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME));
        ArrayList<Coordinators> coords = new ArrayList<>();
        String coordName1=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_NAME_1));
        String coordId1=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_ID_1));
        Long coordPhone1=readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_PHONE_1));
        String coordName2=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_NAME_2));
        String coordId2=readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_ID_2));
        Long coordPhone2=readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_PHONE_2));
        coords.add(new Coordinators(coordName1, coordId1, coordPhone1));
        coords.add(new Coordinators(coordName2, coordId2, coordPhone2));
        EventDetails ev = new EventDetails(eventName, clubName, category, description, rules,venue, prizes, photourl, eventId, startTime, endTime, fee, coords);
        return ev;
    }

    public int getCount(){
        int i=0;
        SQLiteDatabase db=getReadableDatabase();
        String[] projection={Schema.DbEntry.EVENT_ID_COLUMN_NAME};
        Cursor cursor=db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME,projection,null,null,null,null,null);
        i=cursor.getCount();
        cursor.close();
        return i;
    }
}
