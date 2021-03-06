package com.manan.dev.ec2018app.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.manan.dev.ec2018app.Models.Coordinators;
import com.manan.dev.ec2018app.Models.EventDetails;
import com.manan.dev.ec2018app.Models.QRTicketModel;

import java.util.ArrayList;

/**
 * Created by nisha on 2/16/2018.
 */

public class DatabaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ElementsCulmyca.db";
    private static final String TAG = "DbHelper";
    private Context context;

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(dbUtils.CREATE_TABLE);
        sqLiteDatabase.execSQL(dbUtils.CREATE_TABLE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i == 1 && i1 == 2) {
            Log.d("yatin", String.valueOf(i + " " + i1));
            db.execSQL("DROP TABLE IF EXISTS " + Schema.DbEntry.EVENT_LIST_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Schema.DbEntry.QR_TICKET_TABLE_NAME);
            onCreate(db);
        }
    }

    public void addEntryToDb(EventDetails event) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if (!checkIfValueExists(event.getmEventId())) {
            values.put(Schema.DbEntry.EVENT_ID_COLUMN_NAME, event.getmEventId());
            values.put(Schema.DbEntry.EVENT_UNIQUE_ID, event.getmUniqueKey());
            values.put(Schema.DbEntry.EVENT_NAME_COLUMN_NAME, event.getmName());
            values.put(Schema.DbEntry.EVENT_CLUB_COLUMN_NAME, event.getmClubname());
            values.put(Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME, event.getmCategory());
            values.put(Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME, event.getmDesc());
            values.put(Schema.DbEntry.EVENT_RULES_COLUMN_NAME, event.getmRules());
            values.put(Schema.DbEntry.EVENT_VENUE_COLUMN_NAME, event.getmVenue());
            values.put(Schema.DbEntry.EVENT_FEE_COLUMN_NAME, event.getmFees());
            values.put(Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME, event.getmStartTime());
            values.put(Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME, event.getmEndTime());
            values.put(Schema.DbEntry.EVENT_PHOTO, event.getmPhotoUrl());
            values.put(Schema.DbEntry.EVENT_TEAM_SIZE, event.getmEventTeamSize());
            values.put(Schema.DbEntry.EVENT_PRIZES_1, event.getmPrizes().get(0));
            values.put(Schema.DbEntry.EVENT_PRIZES_2, event.getmPrizes().get(1));
            values.put(Schema.DbEntry.EVENT_PRIZES_3, event.getmPrizes().get(2));
            values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_1, event.getmCoordinators().get(0).getmCoordId());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_1, event.getmCoordinators().get(0).getmCoordName());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_1, event.getmCoordinators().get(0).getmCoordPhone());
            if (event.getmCoordinators().size() == 2) {
                values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_2, event.getmCoordinators().get(1).getmCoordId());
                values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_2, event.getmCoordinators().get(1).getmCoordName());
                values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_2, event.getmCoordinators().get(1).getmCoordPhone());
            }
            db.insert(Schema.DbEntry.EVENT_LIST_TABLE_NAME, null, values);
        }
        db.close();
    }
    public void addTicketsToDb(QRTicketModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if (!checkIfValueExists1(model.getEventID())){
            values.put(Schema.DbEntry.EVENT_ID, model.getEventID());
            values.put(Schema.DbEntry.QR_CODE, model.getQRcode());
            values.put(Schema.DbEntry.PAYMENT_STATUS, model.getPaymentStatus());
            values.put(Schema.DbEntry.ARRIVAL_STATUS,model.getArrivalStatus());
            db.insert(Schema.DbEntry.QR_TICKET_TABLE_NAME , null , values);
        }
        db.close();
    }

    public ArrayList<EventDetails> retreiveCategory(String clubName) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<EventDetails> eventList = new ArrayList<>();
        String[] projection = {Schema.DbEntry.EVENT_ID_COLUMN_NAME, Schema.DbEntry.EVENT_NAME_COLUMN_NAME
                , Schema.DbEntry.EVENT_CLUB_COLUMN_NAME, Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME
                , Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME, Schema.DbEntry.EVENT_RULES_COLUMN_NAME
                , Schema.DbEntry.EVENT_VENUE_COLUMN_NAME, Schema.DbEntry.EVENT_FEE_COLUMN_NAME
                , Schema.DbEntry.EVENT_PHOTO, Schema.DbEntry.EVENT_COORDINATOR_NAME_1, Schema.DbEntry.EVENT_COORDINATOR_PHONE_1
                , Schema.DbEntry.EVENT_COORDINATOR_ID_1, Schema.DbEntry.EVENT_COORDINATOR_NAME_2, Schema.DbEntry.EVENT_COORDINATOR_PHONE_2
                , Schema.DbEntry.EVENT_COORDINATOR_ID_2, Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME
                , Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME, Schema.DbEntry.EVENT_PRIZES_1, Schema.DbEntry.EVENT_PRIZES_2
                , Schema.DbEntry.EVENT_PRIZES_3, Schema.DbEntry.EVENT_TEAM_SIZE, Schema.DbEntry.EVENT_UNIQUE_ID};
        Cursor readCursor = db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME, projection, Schema.DbEntry.EVENT_CLUB_COLUMN_NAME + " = ?", new String[]{clubName}, null, null, null);
        readCursor.moveToFirst();
        int totalEvents = readCursor.getCount();
        while (totalEvents > 0) {
            totalEvents--;
            EventDetails event = new EventDetails();
            event = retrieveEvents(readCursor);
            eventList.add(event);
            readCursor.moveToNext();
        }
        readCursor.close();
        db.close();
        return eventList;
    }
    public ArrayList<QRTicketModel> retrieveAllTickets() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<QRTicketModel> qrTickets = new ArrayList<>();
        String[] projection = {Schema.DbEntry.EVENT_ID, Schema.DbEntry.QR_CODE
                , Schema.DbEntry.PAYMENT_STATUS, Schema.DbEntry.ARRIVAL_STATUS};
        Cursor readCursor = db.query(Schema.DbEntry.QR_TICKET_TABLE_NAME, projection, null,null, null, null, null);
        readCursor.moveToFirst();
        int totalTickets = readCursor.getCount();
        while (totalTickets > 0) {
            totalTickets--;
            QRTicketModel tickets = new QRTicketModel();
            tickets = retrieveTickets(readCursor);
            qrTickets.add(tickets);
            readCursor.moveToNext();
        }
        readCursor.close();
        db.close();
        return qrTickets;
    }

    public EventDetails retreiveEventsByID(String EventId) {
        SQLiteDatabase db = getReadableDatabase();
        EventDetails ev = new EventDetails();
        String[] projection = {Schema.DbEntry.EVENT_ID_COLUMN_NAME, Schema.DbEntry.EVENT_NAME_COLUMN_NAME
                , Schema.DbEntry.EVENT_CLUB_COLUMN_NAME, Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME
                , Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME, Schema.DbEntry.EVENT_RULES_COLUMN_NAME
                , Schema.DbEntry.EVENT_VENUE_COLUMN_NAME, Schema.DbEntry.EVENT_FEE_COLUMN_NAME
                , Schema.DbEntry.EVENT_PHOTO, Schema.DbEntry.EVENT_COORDINATOR_NAME_1, Schema.DbEntry.EVENT_COORDINATOR_PHONE_1
                , Schema.DbEntry.EVENT_COORDINATOR_ID_1, Schema.DbEntry.EVENT_COORDINATOR_NAME_2, Schema.DbEntry.EVENT_COORDINATOR_PHONE_2
                , Schema.DbEntry.EVENT_COORDINATOR_ID_2, Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME
                , Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME, Schema.DbEntry.EVENT_PRIZES_1, Schema.DbEntry.EVENT_PRIZES_2
                , Schema.DbEntry.EVENT_PRIZES_3, Schema.DbEntry.EVENT_TEAM_SIZE, Schema.DbEntry.EVENT_UNIQUE_ID};
        Cursor readCursor = db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME, projection, Schema.DbEntry.EVENT_ID_COLUMN_NAME + " = ?", new String[]{EventId}, null, null, null);
        readCursor.moveToFirst();
        ev = retrieveEvents(readCursor);
        readCursor.close();
        db.close();
        return ev;
    }
    public QRTicketModel retrieveTicketsByID(String EventId){
        QRTicketModel qrTicketModel = new QRTicketModel();
        if(checkIfValueExists1(EventId)) {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {Schema.DbEntry.EVENT_ID, Schema.DbEntry.QR_CODE
                    , Schema.DbEntry.PAYMENT_STATUS, Schema.DbEntry.ARRIVAL_STATUS};
            Cursor readCursor = db.query(Schema.DbEntry.QR_TICKET_TABLE_NAME, projection, Schema.DbEntry.EVENT_ID + " = ?", new String[]{EventId}, null, null, null);
            readCursor.moveToFirst();
            qrTicketModel = retrieveTickets(readCursor);
            readCursor.close();

            return qrTicketModel;
        }
        qrTicketModel.setEventID("null");
        return qrTicketModel;
    }


    public String retrieveEventIdByName(String eventName){
        SQLiteDatabase db = getReadableDatabase();
        if(checkIfValueByNameExists(eventName)) {
            String[] projection = {Schema.DbEntry.EVENT_ID_COLUMN_NAME};
            Cursor readCursor = db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME, projection, Schema.DbEntry.EVENT_NAME_COLUMN_NAME + " = ?", new String[]{eventName}, null, null, null);
            readCursor.moveToFirst();
            String eventId = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_ID_COLUMN_NAME));
            readCursor.close();
            db.close();
            return eventId;
        }
        else {
            db.close();
            return "wrong";
        }
    }
    public QRTicketModel retrieveTickets(Cursor readCursor) {
        String eventId = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_ID));
        String qrCode = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.QR_CODE));
        Integer paymentStatus = readCursor.getInt(readCursor.getColumnIndexOrThrow(Schema.DbEntry.PAYMENT_STATUS));
        Integer arrivalStatus = readCursor.getInt(readCursor.getColumnIndexOrThrow(Schema.DbEntry.ARRIVAL_STATUS));
        return new QRTicketModel(paymentStatus,arrivalStatus,qrCode,eventId);
    }
    public EventDetails retrieveEvents(Cursor readCursor) {
        String eventId = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_ID_COLUMN_NAME));
        String eventName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_NAME_COLUMN_NAME));
        String category = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME));
        String description = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME));
        String rules = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_RULES_COLUMN_NAME));
        String venue = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_VENUE_COLUMN_NAME));
        Long fee = readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_FEE_COLUMN_NAME));
        Integer uniqueKey = readCursor.getInt(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_UNIQUE_ID));

        String prize1 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PRIZES_1));
        String prize2 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PRIZES_2));
        String prize3 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PRIZES_3));
        ArrayList<String> prizes = new ArrayList<>();
        prizes.add(prize1);
        prizes.add(prize2);
        prizes.add(prize3);

        String eventTeamSize = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_TEAM_SIZE));
        String clubName = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_CLUB_COLUMN_NAME));
        String photourl = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_PHOTO));

        Long startTime = readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME));
        Long endTime = readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME));

        ArrayList<Coordinators> coords = new ArrayList<>();
        String coordName1 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_NAME_1));
        String coordId1 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_ID_1));
        Long coordPhone1 = readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_PHONE_1));
        String coordName2 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_NAME_2));
        String coordId2 = readCursor.getString(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_ID_2));
        Long coordPhone2 = readCursor.getLong(readCursor.getColumnIndexOrThrow(Schema.DbEntry.EVENT_COORDINATOR_PHONE_2));
        coords.add(new Coordinators(coordName1, coordId1, coordPhone1));
        coords.add(new Coordinators(coordName2, coordId2, coordPhone2));


        return new EventDetails(eventName, clubName, category, description, rules, venue, photourl, eventId, eventTeamSize, startTime, endTime, fee, coords, prizes, uniqueKey);
    }

    public void updateDb(EventDetails event) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Schema.DbEntry.EVENT_CATEGORY_COLUMN_NAME, event.getmCategory());
        values.put(Schema.DbEntry.EVENT_UNIQUE_ID, event.getmUniqueKey());
        values.put(Schema.DbEntry.EVENT_DESCRIPTION_COLUMN_NAME, event.getmDesc());
        values.put(Schema.DbEntry.EVENT_RULES_COLUMN_NAME, event.getmRules());
        values.put(Schema.DbEntry.EVENT_VENUE_COLUMN_NAME, event.getmVenue());
        values.put(Schema.DbEntry.EVENT_FEE_COLUMN_NAME, event.getmFees());
        values.put(Schema.DbEntry.EVENT_START_TIME_COLUMN_NAME, event.getmStartTime());
        values.put(Schema.DbEntry.EVENT_END_TIME_COLUMN_NAME, event.getmEndTime());
        values.put(Schema.DbEntry.EVENT_PHOTO, event.getmPhotoUrl());
        values.put(Schema.DbEntry.EVENT_TEAM_SIZE, event.getmEventTeamSize());
        if (event.getmPrizes().size() > 0) {
            values.put(Schema.DbEntry.EVENT_PRIZES_1, event.getmPrizes().get(0));
            values.put(Schema.DbEntry.EVENT_PRIZES_2, event.getmPrizes().get(1));
            values.put(Schema.DbEntry.EVENT_PRIZES_3, event.getmPrizes().get(2));
        }
        if (event.getmCoordinators().size() > 0) {
            values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_1, event.getmCoordinators().get(0).getmCoordId());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_1, event.getmCoordinators().get(0).getmCoordName());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_1, event.getmCoordinators().get(0).getmCoordPhone());
        }
        if (event.getmCoordinators().size() == 2) {
            values.put(Schema.DbEntry.EVENT_COORDINATOR_ID_2, event.getmCoordinators().get(1).getmCoordId());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_NAME_2, event.getmCoordinators().get(1).getmCoordName());
            values.put(Schema.DbEntry.EVENT_COORDINATOR_PHONE_2, event.getmCoordinators().get(1).getmCoordPhone());
        }

        db.update(Schema.DbEntry.EVENT_LIST_TABLE_NAME, values, Schema.DbEntry.EVENT_NAME_COLUMN_NAME + "=?", new String[]{event.getmName()});
        db.close();
    }
    public void updateDbTickets(QRTicketModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Schema.DbEntry.EVENT_ID, model.getEventID());
        values.put(Schema.DbEntry.QR_CODE, model.getQRcode());
        values.put(Schema.DbEntry.PAYMENT_STATUS, model.getPaymentStatus());
        values.put(Schema.DbEntry.ARRIVAL_STATUS,model.getArrivalStatus());
        db.update(Schema.DbEntry.QR_TICKET_TABLE_NAME , values ,Schema.DbEntry.EVENT_ID + "=?",new String[]{model.getEventID()} );
        db.close();
    }
    public int getCount() {
        int i = 0;
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Schema.DbEntry.EVENT_ID_COLUMN_NAME};
        Cursor cursor = db.query(Schema.DbEntry.EVENT_LIST_TABLE_NAME, projection, null, null, null, null, null);
        i = cursor.getCount();
        cursor.close();
        return i;
    }
    public int getTicketCount() {
        int i = 0;
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {Schema.DbEntry.EVENT_ID};
        Cursor cursor = db.query(Schema.DbEntry.QR_TICKET_TABLE_NAME, projection, null, null, null, null, null);
        i = cursor.getCount();
        cursor.close();
        db.close();
        return i;
    }

    public Boolean checkIfValueExists(String eventId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Schema.DbEntry.EVENT_LIST_TABLE_NAME + " WHERE " + Schema.DbEntry.EVENT_ID_COLUMN_NAME + " =?";
        Cursor cs = db.rawQuery(query, new String[]{eventId});
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }

    private Boolean checkIfValueByNameExists(String eventName) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Schema.DbEntry.EVENT_LIST_TABLE_NAME + " WHERE " + Schema.DbEntry.EVENT_NAME_COLUMN_NAME + " =?";
        Cursor cs = db.rawQuery(query, new String[]{eventName});
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        return true;
    }
    public Boolean checkIfValueExists1(String eventId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + Schema.DbEntry.QR_TICKET_TABLE_NAME + " WHERE " + Schema.DbEntry.EVENT_ID + " =?";
        Cursor cs = db.rawQuery(query, new String[]{eventId});
        if (cs.getCount() <= 0) {
            cs.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }


    public void deleteTickets()
    { SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ Schema.DbEntry.QR_TICKET_TABLE_NAME);
        db.close();

    }
}
