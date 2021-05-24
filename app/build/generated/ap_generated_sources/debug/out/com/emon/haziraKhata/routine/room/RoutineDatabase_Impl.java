package com.emon.haziraKhata.routine.room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class RoutineDatabase_Impl extends RoutineDatabase {
  private volatile RoutineDao _routineDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `RoutineItem` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mStartTime` INTEGER, `mEndTime` INTEGER, `mName` TEXT, `mLocation` TEXT, `mColor` INTEGER NOT NULL, `mAllDay` INTEGER NOT NULL, `dateIfTemporary` INTEGER, `type` TEXT, `triggerAlarm` INTEGER NOT NULL, `isPermanent` INTEGER NOT NULL, `details` TEXT, `selectedDayList` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"215a30492caf5ab0b0e5c6b3e10893ec\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `RoutineItem`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRoutineItem = new HashMap<String, TableInfo.Column>(13);
        _columnsRoutineItem.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRoutineItem.put("mStartTime", new TableInfo.Column("mStartTime", "INTEGER", false, 0));
        _columnsRoutineItem.put("mEndTime", new TableInfo.Column("mEndTime", "INTEGER", false, 0));
        _columnsRoutineItem.put("mName", new TableInfo.Column("mName", "TEXT", false, 0));
        _columnsRoutineItem.put("mLocation", new TableInfo.Column("mLocation", "TEXT", false, 0));
        _columnsRoutineItem.put("mColor", new TableInfo.Column("mColor", "INTEGER", true, 0));
        _columnsRoutineItem.put("mAllDay", new TableInfo.Column("mAllDay", "INTEGER", true, 0));
        _columnsRoutineItem.put("dateIfTemporary", new TableInfo.Column("dateIfTemporary", "INTEGER", false, 0));
        _columnsRoutineItem.put("type", new TableInfo.Column("type", "TEXT", false, 0));
        _columnsRoutineItem.put("triggerAlarm", new TableInfo.Column("triggerAlarm", "INTEGER", true, 0));
        _columnsRoutineItem.put("isPermanent", new TableInfo.Column("isPermanent", "INTEGER", true, 0));
        _columnsRoutineItem.put("details", new TableInfo.Column("details", "TEXT", false, 0));
        _columnsRoutineItem.put("selectedDayList", new TableInfo.Column("selectedDayList", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoutineItem = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoutineItem = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRoutineItem = new TableInfo("RoutineItem", _columnsRoutineItem, _foreignKeysRoutineItem, _indicesRoutineItem);
        final TableInfo _existingRoutineItem = TableInfo.read(_db, "RoutineItem");
        if (! _infoRoutineItem.equals(_existingRoutineItem)) {
          throw new IllegalStateException("Migration didn't properly handle RoutineItem(com.emon.haziraKhata.routine.RoutineItem).\n"
                  + " Expected:\n" + _infoRoutineItem + "\n"
                  + " Found:\n" + _existingRoutineItem);
        }
      }
    }, "215a30492caf5ab0b0e5c6b3e10893ec", "8e74f62e9f5702fd71a090da626f612b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "RoutineItem");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `RoutineItem`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public RoutineDao routineDao() {
    if (_routineDao != null) {
      return _routineDao;
    } else {
      synchronized(this) {
        if(_routineDao == null) {
          _routineDao = new RoutineDao_Impl(this);
        }
        return _routineDao;
      }
    }
  }
}
