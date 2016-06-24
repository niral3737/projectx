package projectx.itgo.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projectx.itgo.com.models.AppUser;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "projectx.db";
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_ID = "_id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_COLUMN_USERNAME + " TEXT, " +
                USER_COLUMN_PASSWORD + " TEXT " + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_ID, 1);
        values.put(USER_COLUMN_USERNAME, userName);
        values.put(USER_COLUMN_PASSWORD, password);

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public AppUser getUser() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE_NAME, new String[]{USER_COLUMN_ID,
                        USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD}, USER_COLUMN_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            AppUser user = new AppUser(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
            return user;
        }
        return null;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME, USER_COLUMN_ID + " = ?",
                new String[]{String.valueOf(1)});
        db.close();
    }
}
