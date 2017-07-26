package com.micutu.trafictube.Repository.SqlRepository.Contracts;

import android.provider.BaseColumns;

public class VoteContract {

    private VoteContract() {}

    public static class VoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "vote";
        public static final String COLUMN_NAME_POST_ID = "postId";
    }

}
