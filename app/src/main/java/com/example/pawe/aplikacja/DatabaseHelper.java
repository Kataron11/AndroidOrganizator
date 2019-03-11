package com.example.pawe.aplikacja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Org";
    private static final int DATABASE_VERSION = 3;
    SQLiteDatabase db;

    public static final String TBL_PER = "per";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_NAME_USER = "name";
    public static final String COLUMN_PASSWORD_USER = "password";
    public static final String COLUMN_EMAIL_USER = "email";
    public static final String COLUMN_CODE_USER = "code";


    public static final String TBL_NOT = "note";
    public static final String COLUMN_ID_NOT = "id_note";
    public static final String COLUMN_NAME_NOT = "title";
    public static final String COLUMN_TEXT_NOT = "content";
    public static final String COLUMN_USER_NOT = "user_n";


    public static final String TBL_CL = "calendar";
    public static final String COLUMN_ID_CL = "id_calendar";
    public static final String COLUMN_NAME_CL = "title_calendar";
    public static final String COLUMN_DATA_CL = "content_cal";
    public static final String COLUMN_TIMES_CL = "time_start";
    public static final String COLUMN_TIMEE_CL = "time_end";
    public static final String COLUMN_USER_CL = "user_c";

    public static final String TBL_CT = "contact";
    public static final String COLUMN_ID_CT = "id_contact";
    public static final String COLUMN_NAME_CT = "name";
    public static final String COLUMN_NUMBER_CT = "number";
    public static final String COLUMN_DATA_CT = "birthday";
    public static final String COLUMN_CAT_CT = "catalog_con";
    public static final String COLUMN_USER_CT = "user_con";



    public static final String TBL_CAT = "catalog";
    public static final String COLUMN_ID_CAT = "id_catalog";
    public static final String COLUMN_NAME_CAT = "name";

    private String TBL_CREATE_PER = "CREATE TABLE " + TBL_PER + " ("
            + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_USER + " text, "
            + COLUMN_PASSWORD_USER + " text, "
            + COLUMN_CODE_USER + " text, "
            + COLUMN_EMAIL_USER + " text);";

    private String TBL_CREATE_NOT = "CREATE TABLE " + TBL_NOT + " ("
            + COLUMN_ID_NOT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_NOT + " text, "
            + COLUMN_TEXT_NOT + " text, "
            + COLUMN_USER_NOT + " INTEGER, "
            + " FOREIGN KEY(" + COLUMN_USER_NOT + ") REFERENCES " + TBL_PER + "(" + COLUMN_ID_USER + "));";

    private String TBL_CREATE_CL = "CREATE TABLE " + TBL_CL + " ("
            + COLUMN_ID_CL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_CL + " text, "
            + COLUMN_DATA_CL + " text, "
            + COLUMN_TIMES_CL + " text,"
            + COLUMN_TIMEE_CL + " text,"
            + COLUMN_USER_CL + " INTEGER, "
            + " FOREIGN KEY(" + COLUMN_USER_CL + ") REFERENCES " + TBL_PER + "(" + COLUMN_ID_USER + "));";

    private String TBL_CREATE_CAT = "CREATE TABLE " + TBL_CAT + " ("
            + COLUMN_ID_CAT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_CAT + " text);";

    private String TBL_CREATE_CT = "CREATE TABLE " + TBL_CT + " ("
            + COLUMN_ID_CT + " INTEGER PRIMARY KEY AUtOINCREMENT, "
            + COLUMN_NAME_CT + " text, "
            + COLUMN_NUMBER_CT + " text, "
            + COLUMN_DATA_CT + " text,"
            + COLUMN_CAT_CT + " INTEGER, "
            + COLUMN_USER_CT + " INTEGER, "
            + " FOREIGN KEY(" + COLUMN_CAT_CT + ") REFERENCES " + TBL_CAT + "(" + COLUMN_ID_CAT + "), "
            + " FOREIGN KEY(" + COLUMN_USER_CT + ") REFERENCES " + TBL_PER + "(" + COLUMN_ID_USER + "));";




    private String TBL_USER_DROP = "DROP TABLE IF EXISTS " + TBL_PER;
    private String TBL_NOT_DROP = "DROP TABLE IF EXISTS " + TBL_NOT;
    private String TBL_CL_DROP = "DROP TABLE IF EXISTS " + TBL_CL;
    private String TBL_CT_DROP = "DROP TABLE IF EXISTS " + TBL_CT;
    private String TBL_CAT_DROP = "DROP TABLE IF EXISTS " + TBL_CAT;

    private String Cataloge = "INSERT INTO " + TBL_CAT + " VALUES (1,'Osobiste');";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tworzenie bazy
        db.execSQL(TBL_CREATE_PER);
        db.execSQL(TBL_CREATE_NOT);
        db.execSQL(TBL_CREATE_CL);
        db.execSQL(TBL_CREATE_CT);
        db.execSQL(TBL_CREATE_CAT);
        db.execSQL(Cataloge);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(TBL_USER_DROP);
        db.execSQL(TBL_NOT_DROP);
        db.execSQL(TBL_CL_DROP);
        db.execSQL(TBL_CT_DROP);
        db.execSQL(TBL_CAT_DROP);
        // Stworzenie od nowa bazy
        onCreate(db);
    }

    public void addUser(String name, String password, String code, String email) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_NAME_USER, name);
        values.put(COLUMN_PASSWORD_USER, password);
        values.put(COLUMN_CODE_USER, code);
        values.put(COLUMN_EMAIL_USER, email);

        // Inserting Row
        db.insert(TBL_PER, null, values);
        db.close();
    }

    public void updateUser(String email, String password) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PASSWORD_USER, password);
        values.put(COLUMN_EMAIL_USER, email);
        String[] whereArgs = {email};
        db.update(TBL_PER, values, COLUMN_EMAIL_USER + " = ?", whereArgs);
        db.close();
    }

    public boolean queryUser(String name, String password){
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_NAME_USER, COLUMN_PASSWORD_USER}, COLUMN_NAME_USER + " =? and " + COLUMN_PASSWORD_USER + " =?",
                new String[]{name, password}, null, null, null, "1");
        if (cursor.moveToFirst()) {
            return true;
        }

        cursor.close();
        db.close();

        return false;
    }

    public boolean recoverUser(String email, String code){
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_ID_USER,
                        COLUMN_EMAIL_USER, COLUMN_CODE_USER}, COLUMN_EMAIL_USER + " =? and " + COLUMN_CODE_USER + " =?",
                new String[]{email, code}, null, null, null, "1");
        if (cursor.moveToFirst()) {
            return true;
        }

        cursor.close();
        db.close();

        return false;
    }

    public String userLogin(String login){
        db = this.getReadableDatabase();
        String blank= "";
        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_NAME_USER}, COLUMN_NAME_USER + " =?", new String[]{login}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USER));
            return  name;

        }

        cursor.close();
        db.close();
        return blank;

    }

    public String userEmail(String email){
        db = this.getReadableDatabase();
        String blank= "";
        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_EMAIL_USER}, COLUMN_EMAIL_USER + " =?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USER));
            return  name;

        }

        cursor.close();
        db.close();
        return blank;

    }

    public String userPassword(String password){
        db = this.getReadableDatabase();
        String blank= "";
        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_PASSWORD_USER}, COLUMN_EMAIL_USER + " =?", new String[]{password}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_USER));
            return  name;

        }

        cursor.close();
        db.close();
        return blank;
    }

    public int getUser(String name) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_PER, new String[]{COLUMN_ID_USER}, COLUMN_NAME_USER + " =?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_USER));
            return  index;
        }

        cursor.close();
        db.close();

        return 0;

    }

    public void addCalendarList(String title, String task, int user, String timeStart, String timeEnd) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CL, title);
        values.put(COLUMN_DATA_CL, task);
        values.put(COLUMN_USER_CL, user);
        values.put(COLUMN_TIMES_CL, timeStart);
        values.put(COLUMN_TIMEE_CL, timeEnd);

        db.insert(TBL_CL, null, values);
        db.close();

    }

    public void deleteCalendar(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_CL,COLUMN_NAME_CL + " = ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getCalendarList(String date) {
        ArrayList<String> calendarList = new ArrayList<>();
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_CL, new String[]{COLUMN_ID_CL, COLUMN_NAME_CL, COLUMN_DATA_CL, COLUMN_USER_CL, COLUMN_TIMES_CL, COLUMN_TIMEE_CL}, COLUMN_DATA_CL + " =?", new String[]{date}, null, null, COLUMN_TIMES_CL +" ASC", null);

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME_CL);


            calendarList.add(cursor.getString(index));



        }
        cursor.close();
        db.close();

        return calendarList;
    }

    public String getCalendarTime(String name) {
        String blank="";
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_CL, new String[]{COLUMN_TIMES_CL}, COLUMN_NAME_CL + " =?", new String[]{name}, null, null, null, null);
         if (cursor.moveToFirst()) {
             String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIMES_CL));
            return  time;

        }
        cursor.close();
        db.close();

        return blank;
    }

    public String getCalendarTimeEnd(String name) {

        db = this.getReadableDatabase();
        String blank="";
        Cursor cursor = db.query(TBL_CL, new String[]{COLUMN_TIMEE_CL}, COLUMN_NAME_CL + " =?", new String[]{name}, null, null, null, null);
        if (cursor.moveToFirst()) {
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIMEE_CL));
            return  time;

        }
        cursor.close();
        db.close();

        return blank;
    }


    public void addNoteList(String title, String content, int user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NOT, title);
        values.put(COLUMN_TEXT_NOT, content);
        values.put(COLUMN_USER_NOT, user);
        db.insert(TBL_NOT, null, values);
        db.close();

    }

    public ArrayList<String> getNoteList() {
        ArrayList<String> noteList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_NOT, new String[]{COLUMN_ID_NOT, COLUMN_NAME_NOT, COLUMN_TEXT_NOT, COLUMN_USER_NOT} ,null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME_NOT);
            noteList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();

        return noteList;
    }

    public String contentNote(String title){
        db = this.getReadableDatabase();
        String blank= "";
        Cursor cursor = db.query(TBL_NOT, new String[]{COLUMN_TEXT_NOT}, COLUMN_NAME_NOT + " =?", new String[]{title}, null, null, null);
        if (cursor.moveToFirst()) {
            String index = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT_NOT));
            return  index;

        }

        cursor.close();
        db.close();
        return blank;
    }

    public void deleteNote(String title){
        db= this.getWritableDatabase();
        db.delete(TBL_NOT,COLUMN_NAME_NOT + " = ?",new String[]{title});
        db.close();
    }

    public void updateNote(String title, String content, int id) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_NOT, title);
        values.put(COLUMN_TEXT_NOT, content);


        db.update(TBL_NOT, values, COLUMN_ID_NOT + " = " + id ,null);
        db.close();
    }

    public String noteSearch(String title){
        db = this.getWritableDatabase();

        String blank= "";
        Cursor cursor = db.query(TBL_NOT, new String[]{COLUMN_NAME_NOT}, COLUMN_NAME_NOT + " =?", new String[]{title}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NOT));
            return  name;

        }

        cursor.close();
        db.close();
        return blank;

            }


    public int getIdNote(String name) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_NOT, new String[]{COLUMN_ID_NOT}, COLUMN_NAME_NOT + " =?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NOT));
            return  index;
        }

        cursor.close();
        db.close();

        return 0;

    }

    public ArrayList<String> getContactList(int cat) {
        ArrayList<String> contactList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_CT, new String[]{COLUMN_ID_CT, COLUMN_NAME_CT,
                COLUMN_NUMBER_CT, COLUMN_USER_CT, COLUMN_CAT_CT}
                ,COLUMN_CAT_CT + " = " + cat ,null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME_CT);
            contactList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();

        return contactList;
    }



    public void addContact(String name, String number, int user, int cataloge ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CT, name);
        values.put(COLUMN_NUMBER_CT, number);
        values.put(COLUMN_USER_CT,user);
        values.put(COLUMN_CAT_CT, cataloge);
        db.insert(TBL_CT,null,values);
        db.close();

    }

    public void updateContact(String data, int id) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATA_CT, data);


        db.update(TBL_CT, values, COLUMN_ID_CT + " = " + id, null );
        db.close();
    }


    public String getDateContact (int id) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_CT, new String[]{COLUMN_DATA_CT}, COLUMN_ID_CT + " = " + id , null, null, null, null);
        if (cursor.moveToFirst()) {
            String index = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_CT));
            return  index;
        }

        cursor.close();
        db.close();

        return " ";

    }

    public String contentContact(String name) {

        db = this.getReadableDatabase();
        String blank = "";
        Cursor cursor = db.query(TBL_CT, new String[]{COLUMN_NUMBER_CT}, COLUMN_NAME_CT + " =?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            String index = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER_CT));
            return index;

        }

        cursor.close();
        db.close();
        return blank;


    }

    public void deleteContact(String name){
        db = this.getReadableDatabase();
        db.delete(TBL_CT,COLUMN_NAME_CT + " = ?",new String[]{name});
        db.close();
    }



    public void updateContact(String name, String number, int id) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_CT, name);
        values.put(COLUMN_NUMBER_CT, number);


        db.update(TBL_CT, values, COLUMN_ID_CT + " = " + id ,null);

        db.close();
    }


    public int getIdContact(String name) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_CT, new String[]{COLUMN_ID_CT}, COLUMN_NAME_CT + " =?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CT));
            return  index;
        }

        cursor.close();
        db.close();

        return 0;

    }

    public ArrayList<String> getCatalogeList() {
        ArrayList<String> catalogList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_CAT, new String[]{COLUMN_ID_CAT, COLUMN_NAME_CAT} ,null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME_CAT);
            catalogList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();

        return catalogList;
    }


    public int getIdCatalog(String name) {
        db = this.getReadableDatabase();

        Cursor cursor = db.query(TBL_CAT, new String[]{COLUMN_ID_CAT}, COLUMN_NAME_CAT + " =?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CAT));
            return  index;
        }

        cursor.close();
        db.close();

        return 0;

    }

    public void addCatalog(String name){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CAT, name);

        db.insert(TBL_CAT,null,values);
        db.close();
    }


    public void changeContanct(int cataloge , int id ){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CAT_CT, cataloge);



        db.update(TBL_CT, values, COLUMN_ID_CT + " = " + id ,null);

        db.close();
    }
}


