package com.example.asd;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String _TABLENAME0 = "cocktail_table";
        public static final String _TABLENAME1 = "user_table";
        public static final String NAME = "name";
        public static final String SUGAR = "sugar";
        public static final String ALCOHOL = "alcohol";
        public static final String BODY = "body";
        public static final String UNIQUE_ = "unique_";
        public static final String BASE = "base";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +BASE+" text not null , "
                +NAME+" text not null , "
                +SUGAR +" integer not null , "
                +ALCOHOL + " integer not null , "
                +BODY + " integer not null , "
                +UNIQUE_ + " integer not null);";
        public static final String _CREATE1 = "create table if not exists "+_TABLENAME1+"("
                +NAME+" text not null , "
                +SUGAR +" integer not null , "
                +ALCOHOL + " integer not null , "
                +BODY + " integer not null , "
                +UNIQUE_ + " integer not null , "
                +BASE+" text not null , "
                +_ID+" integer primary key not null);";
    }
}
