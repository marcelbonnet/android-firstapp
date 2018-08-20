package com.example.myfirstapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class CondominioProvider extends ContentProvider {
//https://github.com/Miserlou/Android-SDK-Samples/blob/ef7b5365393dc9d039260104480dc254ce784f5c/NotePad/src/com/example/android/notepad/NotePadProvider.java

    private DatabaseHelper mOpenHelper;

    /**
     * The database that the provider uses as its underlying data store
     */
    private static final String DATABASE_NAME = "condominio.db";

    /**
     * The database version
     */
    private static final int DATABASE_VERSION = 3;

    @Override
    public boolean onCreate() {
        // Creates a new helper object. Note that the database itself isn't opened until
        // something tries to access it, and it's only created if it doesn't already exist.
        mOpenHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);

        // Assumes that any failures will be reported by a thrown exception.
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String DUMP = new String("PRAGMA foreign_keys=OFF; BEGIN TRANSACTION;" +
                    "CREATE TABLE IF NOT EXISTS \"unidades\" (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `numero` TEXT NOT NULL UNIQUE," +
                    " `resp_financeiro` TEXT NOT NULL," +
                    " `resp_financeiro_email` TEXT NOT NULL," +
                    " `resp_financeiro_telefone` INTEGER" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS \"cobrancas\" (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `dt_vcto` DATE NOT NULL," +
                    " `valor` NUMERIC NOT NULL," +
                    " `fk_unidade` INTEGER NOT NULL," +
                    " FOREIGN KEY(`fk_unidade`) REFERENCES `unidades`(`id`)" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS \"rateios\" (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `fk_unidade` INTEGER NOT NULL," +
                    " `valor` INTEGER NOT NULL," +
                    " `parcela` INTEGER NOT NULL," +
                    " `dt_vcto` DATE NOT NULL," +
                    " `razao` NUMERIC NOT NULL," +
                    " FOREIGN KEY(`fk_unidade`) REFERENCES `unidades`(`id`)" +
                    ");" +
                    "CREATE TABLE `descontos` (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `valor` INTEGER NOT NULL," +
                    " `fk_unidade` INTEGER NOT NULL," +
                    " `memo` TEXT NOT NULL," +
                    " `fk_despesa` INTEGER NOT NULL," +
                    " FOREIGN KEY(`fk_unidade`) REFERENCES unidades('id')," +
                    " FOREIGN KEY(`fk_despesa`) REFERENCES despesas('id')" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS \"despesas\" (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `dt_evento` DATE NOT NULL," +
                    " `valor` INTEGER NOT NULL," +
                    " `memo` TEXT NOT NULL," +
                    " `dt_inclusao` DATE NOT NULL," +
                    " `nota` BLOB" +
                    ");" +
                    "CREATE TABLE `livro_ocorrencias` (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `fk_unidade` INTEGER NOT NULL," +
                    " `memo` TEXT NOT NULL," +
                    " `dt_evento` DATETIME NOT NULL," +
                    " `dt_inclusao` DATETIME NOT NULL," +
                    " FOREIGN KEY(`fk_unidade`) REFERENCES unidades('id')" +
                    ");" +
                    "CREATE TABLE `livro_atas` (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `documento` BLOB NOT NULL," +
                    " `memo` TEXT NOT NULL," +
                    " `dt_documento` DATE NOT NULL," +
                    " `dt_inclusao` DATETIME NOT NULL" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS \"assinaturas\" (" +
                    " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " `fk_unidade` INTEGER NOT NULL," +
                    " `fk_livro_ata` INTEGER," +
                    " `fk_livro_ocorrencias` INTEGER," +
                    " FOREIGN KEY(`fk_unidade`) REFERENCES `unidades`(`id`)," +
                    " FOREIGN KEY(`fk_livro_ata`) REFERENCES `livro_atas`(`id`)," +
                    " FOREIGN KEY(`fk_livro_ocorrencias`) REFERENCES livro_ocorrencias('id')" +
                    ");" +
                    "DELETE FROM sqlite_sequence;" +
                    "INSERT INTO sqlite_sequence VALUES('unidades',0);" +
                    "INSERT INTO sqlite_sequence VALUES('cobrancas',0);" +
                    "INSERT INTO sqlite_sequence VALUES('rateios',0);" +
                    "INSERT INTO sqlite_sequence VALUES('despesas',0);" +
                    "INSERT INTO sqlite_sequence VALUES('assinaturas',0);" +
                    "COMMIT;");

            db.execSQL(DUMP);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old, int n) {
            Log.w("My First App", "Atualizando de " + old + " para " + n);
            //...
        }
    }
}
