package com.bjw.criminallntent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bjw.criminallntent.db.CrimeCursorWrapper;
import com.bjw.criminallntent.db.CrimeDataBaseHelper;
import com.bjw.criminallntent.db.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeDataBaseHelper(mContext)
                .getWritableDatabase();

    }

    public File getPhotoFile(Crime crime) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PODCASTS);

        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, crime.getPhotoFilename());
    }
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        mDatabase.update(CrimeTable.NAME, getContentValues(crime)
                , CrimeTable.Cols.UUID + "= ?", new String[]{uuidString});
    }

    public void deleteCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + "= ?",
                new String[]{uuidString});
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes
                (CrimeTable.Cols.UUID + "=?", new String[] {id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab  == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }
}
