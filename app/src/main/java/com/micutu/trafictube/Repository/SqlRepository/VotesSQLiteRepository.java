package com.micutu.trafictube.Repository.SqlRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.micutu.trafictube.Repository.SqlRepository.Contracts.VoteContract.VoteEntry;
import com.micutu.trafictube.Repository.SqlRepository.Database.VoteDbHelper;
import com.micutu.trafictube.Repository.VotesRepository;

import java.util.ArrayList;
import java.util.List;

public class VotesSQLiteRepository implements VotesRepository {
    private final Context context;

    public VotesSQLiteRepository(Context context) {
        this.context = context;
    }

    @Override
    public List<Integer> alreadyVoted(List<Integer> postIds) {
        VoteDbHelper mDbHelper = new VoteDbHelper(this.context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String postIdsString = "";
        for (Integer postId : postIds) {
            postIdsString += postId.toString() + ", ";
        }

        postIdsString = postIdsString.substring(0, postIdsString.length() - 2);

        Cursor cursor = db.rawQuery("SELECT " + VoteEntry.COLUMN_NAME_POST_ID + " " +
                "FROM " + VoteEntry.TABLE_NAME +
                " WHERE " + VoteEntry.COLUMN_NAME_POST_ID + " IN (" + postIdsString + ")", new String[]{});

        List<Integer> results = new ArrayList<Integer>();

        while(cursor.moveToNext()) {
            results.add(Integer.parseInt(cursor.getString(0)));
        }

        cursor.close();

        return results;
    }

    @Override
    public Boolean alreadyVoted(Integer postId) {
        VoteDbHelper mDbHelper = new VoteDbHelper(this.context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                VoteEntry._ID,
                VoteEntry.COLUMN_NAME_POST_ID
        };

        String selection = VoteEntry.COLUMN_NAME_POST_ID + " = ?";
        String[] selectionArgs = {postId.toString()};

        Cursor cursor = db.query(VoteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean result = !(cursor.getCount() == 0);

        cursor.close();

        return result;
    }

    @Override
    public void vote(Integer postId) {
        VoteDbHelper mDbHelper = new VoteDbHelper(this.context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VoteEntry.COLUMN_NAME_POST_ID, postId);

        db.insert(VoteEntry.TABLE_NAME, null, values);

        mDbHelper.close();
    }
}
