package com.emon.haziraKhata.routine.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.emon.haziraKhata.routine.RoutineItem;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class RoutineDao_Impl extends RoutineDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRoutineItem;

  private final EntityInsertionAdapter __insertionAdapterOfRoutineItem_1;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRoutineItem;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRoutineItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByID;

  public RoutineDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoutineItem = new EntityInsertionAdapter<RoutineItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `RoutineItem`(`id`,`mStartTime`,`mEndTime`,`mName`,`mLocation`,`mColor`,`mAllDay`,`dateIfTemporary`,`type`,`triggerAlarm`,`isPermanent`,`details`,`selectedDayList`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoutineItem value) {
        stmt.bindLong(1, value.id);
        final Long _tmp;
        _tmp = RoomConverter.fromCalendar(value.mStartTime);
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = RoomConverter.fromCalendar(value.mEndTime);
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp_1);
        }
        if (value.mName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.mName);
        }
        if (value.mLocation == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.mLocation);
        }
        stmt.bindLong(6, value.mColor);
        final int _tmp_2;
        _tmp_2 = value.mAllDay ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        final Long _tmp_3;
        _tmp_3 = RoomConverter.fromCalendar(value.getDateIfTemporary());
        if (_tmp_3 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_3);
        }
        if (value.getType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getType());
        }
        final int _tmp_4;
        _tmp_4 = value.isTriggerAlarm() ? 1 : 0;
        stmt.bindLong(10, _tmp_4);
        final int _tmp_5;
        _tmp_5 = value.isPermanent() ? 1 : 0;
        stmt.bindLong(11, _tmp_5);
        if (value.getDetails() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDetails());
        }
        final String _tmp_6;
        _tmp_6 = RoomConverter.fromIntListToString(value.getSelectedDayList());
        if (_tmp_6 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_6);
        }
      }
    };
    this.__insertionAdapterOfRoutineItem_1 = new EntityInsertionAdapter<RoutineItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `RoutineItem`(`id`,`mStartTime`,`mEndTime`,`mName`,`mLocation`,`mColor`,`mAllDay`,`dateIfTemporary`,`type`,`triggerAlarm`,`isPermanent`,`details`,`selectedDayList`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoutineItem value) {
        stmt.bindLong(1, value.id);
        final Long _tmp;
        _tmp = RoomConverter.fromCalendar(value.mStartTime);
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = RoomConverter.fromCalendar(value.mEndTime);
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp_1);
        }
        if (value.mName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.mName);
        }
        if (value.mLocation == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.mLocation);
        }
        stmt.bindLong(6, value.mColor);
        final int _tmp_2;
        _tmp_2 = value.mAllDay ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        final Long _tmp_3;
        _tmp_3 = RoomConverter.fromCalendar(value.getDateIfTemporary());
        if (_tmp_3 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_3);
        }
        if (value.getType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getType());
        }
        final int _tmp_4;
        _tmp_4 = value.isTriggerAlarm() ? 1 : 0;
        stmt.bindLong(10, _tmp_4);
        final int _tmp_5;
        _tmp_5 = value.isPermanent() ? 1 : 0;
        stmt.bindLong(11, _tmp_5);
        if (value.getDetails() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDetails());
        }
        final String _tmp_6;
        _tmp_6 = RoomConverter.fromIntListToString(value.getSelectedDayList());
        if (_tmp_6 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_6);
        }
      }
    };
    this.__deletionAdapterOfRoutineItem = new EntityDeletionOrUpdateAdapter<RoutineItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `RoutineItem` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoutineItem value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfRoutineItem = new EntityDeletionOrUpdateAdapter<RoutineItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `RoutineItem` SET `id` = ?,`mStartTime` = ?,`mEndTime` = ?,`mName` = ?,`mLocation` = ?,`mColor` = ?,`mAllDay` = ?,`dateIfTemporary` = ?,`type` = ?,`triggerAlarm` = ?,`isPermanent` = ?,`details` = ?,`selectedDayList` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoutineItem value) {
        stmt.bindLong(1, value.id);
        final Long _tmp;
        _tmp = RoomConverter.fromCalendar(value.mStartTime);
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        final Long _tmp_1;
        _tmp_1 = RoomConverter.fromCalendar(value.mEndTime);
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp_1);
        }
        if (value.mName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.mName);
        }
        if (value.mLocation == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.mLocation);
        }
        stmt.bindLong(6, value.mColor);
        final int _tmp_2;
        _tmp_2 = value.mAllDay ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        final Long _tmp_3;
        _tmp_3 = RoomConverter.fromCalendar(value.getDateIfTemporary());
        if (_tmp_3 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, _tmp_3);
        }
        if (value.getType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getType());
        }
        final int _tmp_4;
        _tmp_4 = value.isTriggerAlarm() ? 1 : 0;
        stmt.bindLong(10, _tmp_4);
        final int _tmp_5;
        _tmp_5 = value.isPermanent() ? 1 : 0;
        stmt.bindLong(11, _tmp_5);
        if (value.getDetails() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getDetails());
        }
        final String _tmp_6;
        _tmp_6 = RoomConverter.fromIntListToString(value.getSelectedDayList());
        if (_tmp_6 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_6);
        }
        stmt.bindLong(14, value.id);
      }
    };
    this.__preparedStmtOfDeleteByID = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM RoutineItem WHERE  id = (?)";
        return _query;
      }
    };
  }

  @Override
  void insert(RoutineItem routineItem) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRoutineItem.insert(routineItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  void insertAll(RoutineItem... routineItems) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRoutineItem_1.insert(routineItems);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  void delete(RoutineItem routineItem) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfRoutineItem.handle(routineItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  void updateRoutine(RoutineItem... routineItems) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRoutineItem.handleMultiple(routineItems);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  void updateRoutine(RoutineItem routineItem) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRoutineItem.handle(routineItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  void deleteByID(Long routineItemID) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByID.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (routineItemID == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, routineItemID);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByID.release(_stmt);
    }
  }

  @Override
  LiveData<List<RoutineItem>> getAllLiveRoutines() {
    final String _sql = "SELECT * FROM RoutineItem";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<RoutineItem>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<RoutineItem> compute() {
        if (_observer == null) {
          _observer = new Observer("RoutineItem") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMStartTime = _cursor.getColumnIndexOrThrow("mStartTime");
          final int _cursorIndexOfMEndTime = _cursor.getColumnIndexOrThrow("mEndTime");
          final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("mName");
          final int _cursorIndexOfMLocation = _cursor.getColumnIndexOrThrow("mLocation");
          final int _cursorIndexOfMColor = _cursor.getColumnIndexOrThrow("mColor");
          final int _cursorIndexOfMAllDay = _cursor.getColumnIndexOrThrow("mAllDay");
          final int _cursorIndexOfDateIfTemporary = _cursor.getColumnIndexOrThrow("dateIfTemporary");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final int _cursorIndexOfTriggerAlarm = _cursor.getColumnIndexOrThrow("triggerAlarm");
          final int _cursorIndexOfIsPermanent = _cursor.getColumnIndexOrThrow("isPermanent");
          final int _cursorIndexOfDetails = _cursor.getColumnIndexOrThrow("details");
          final int _cursorIndexOfSelectedDayList = _cursor.getColumnIndexOrThrow("selectedDayList");
          final List<RoutineItem> _result = new ArrayList<RoutineItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RoutineItem _item;
            _item = new RoutineItem();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfMStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfMStartTime);
            }
            _item.mStartTime = RoomConverter.toCalendar(_tmp);
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfMEndTime);
            }
            _item.mEndTime = RoomConverter.toCalendar(_tmp_1);
            _item.mName = _cursor.getString(_cursorIndexOfMName);
            _item.mLocation = _cursor.getString(_cursorIndexOfMLocation);
            _item.mColor = _cursor.getInt(_cursorIndexOfMColor);
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMAllDay);
            _item.mAllDay = _tmp_2 != 0;
            final Calendar _tmpDateIfTemporary;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfDateIfTemporary)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfDateIfTemporary);
            }
            _tmpDateIfTemporary = RoomConverter.toCalendar(_tmp_3);
            _item.setDateIfTemporary(_tmpDateIfTemporary);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item.setType(_tmpType);
            final boolean _tmpTriggerAlarm;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfTriggerAlarm);
            _tmpTriggerAlarm = _tmp_4 != 0;
            _item.setTriggerAlarm(_tmpTriggerAlarm);
            final boolean _tmpIsPermanent;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsPermanent);
            _tmpIsPermanent = _tmp_5 != 0;
            _item.setPermanent(_tmpIsPermanent);
            final String _tmpDetails;
            _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            _item.setDetails(_tmpDetails);
            final List<Integer> _tmpSelectedDayList;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfSelectedDayList);
            _tmpSelectedDayList = RoomConverter.fromStringToIntList(_tmp_6);
            _item.setSelectedDayList(_tmpSelectedDayList);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  List<RoutineItem> getAllRoutines() {
    final String _sql = "SELECT * FROM RoutineItem";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMStartTime = _cursor.getColumnIndexOrThrow("mStartTime");
      final int _cursorIndexOfMEndTime = _cursor.getColumnIndexOrThrow("mEndTime");
      final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("mName");
      final int _cursorIndexOfMLocation = _cursor.getColumnIndexOrThrow("mLocation");
      final int _cursorIndexOfMColor = _cursor.getColumnIndexOrThrow("mColor");
      final int _cursorIndexOfMAllDay = _cursor.getColumnIndexOrThrow("mAllDay");
      final int _cursorIndexOfDateIfTemporary = _cursor.getColumnIndexOrThrow("dateIfTemporary");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfTriggerAlarm = _cursor.getColumnIndexOrThrow("triggerAlarm");
      final int _cursorIndexOfIsPermanent = _cursor.getColumnIndexOrThrow("isPermanent");
      final int _cursorIndexOfDetails = _cursor.getColumnIndexOrThrow("details");
      final int _cursorIndexOfSelectedDayList = _cursor.getColumnIndexOrThrow("selectedDayList");
      final List<RoutineItem> _result = new ArrayList<RoutineItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final RoutineItem _item;
        _item = new RoutineItem();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMStartTime)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMStartTime);
        }
        _item.mStartTime = RoomConverter.toCalendar(_tmp);
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMEndTime)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMEndTime);
        }
        _item.mEndTime = RoomConverter.toCalendar(_tmp_1);
        _item.mName = _cursor.getString(_cursorIndexOfMName);
        _item.mLocation = _cursor.getString(_cursorIndexOfMLocation);
        _item.mColor = _cursor.getInt(_cursorIndexOfMColor);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfMAllDay);
        _item.mAllDay = _tmp_2 != 0;
        final Calendar _tmpDateIfTemporary;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfDateIfTemporary)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfDateIfTemporary);
        }
        _tmpDateIfTemporary = RoomConverter.toCalendar(_tmp_3);
        _item.setDateIfTemporary(_tmpDateIfTemporary);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _item.setType(_tmpType);
        final boolean _tmpTriggerAlarm;
        final int _tmp_4;
        _tmp_4 = _cursor.getInt(_cursorIndexOfTriggerAlarm);
        _tmpTriggerAlarm = _tmp_4 != 0;
        _item.setTriggerAlarm(_tmpTriggerAlarm);
        final boolean _tmpIsPermanent;
        final int _tmp_5;
        _tmp_5 = _cursor.getInt(_cursorIndexOfIsPermanent);
        _tmpIsPermanent = _tmp_5 != 0;
        _item.setPermanent(_tmpIsPermanent);
        final String _tmpDetails;
        _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
        _item.setDetails(_tmpDetails);
        final List<Integer> _tmpSelectedDayList;
        final String _tmp_6;
        _tmp_6 = _cursor.getString(_cursorIndexOfSelectedDayList);
        _tmpSelectedDayList = RoomConverter.fromStringToIntList(_tmp_6);
        _item.setSelectedDayList(_tmpSelectedDayList);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  LiveData<List<RoutineItem>> findAllByIds(int[] userIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM RoutineItem WHERE id IN (");
    final int _inputSize = userIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : userIds) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    return new ComputableLiveData<List<RoutineItem>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<RoutineItem> compute() {
        if (_observer == null) {
          _observer = new Observer("RoutineItem") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMStartTime = _cursor.getColumnIndexOrThrow("mStartTime");
          final int _cursorIndexOfMEndTime = _cursor.getColumnIndexOrThrow("mEndTime");
          final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("mName");
          final int _cursorIndexOfMLocation = _cursor.getColumnIndexOrThrow("mLocation");
          final int _cursorIndexOfMColor = _cursor.getColumnIndexOrThrow("mColor");
          final int _cursorIndexOfMAllDay = _cursor.getColumnIndexOrThrow("mAllDay");
          final int _cursorIndexOfDateIfTemporary = _cursor.getColumnIndexOrThrow("dateIfTemporary");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final int _cursorIndexOfTriggerAlarm = _cursor.getColumnIndexOrThrow("triggerAlarm");
          final int _cursorIndexOfIsPermanent = _cursor.getColumnIndexOrThrow("isPermanent");
          final int _cursorIndexOfDetails = _cursor.getColumnIndexOrThrow("details");
          final int _cursorIndexOfSelectedDayList = _cursor.getColumnIndexOrThrow("selectedDayList");
          final List<RoutineItem> _result = new ArrayList<RoutineItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RoutineItem _item_1;
            _item_1 = new RoutineItem();
            _item_1.id = _cursor.getLong(_cursorIndexOfId);
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfMStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfMStartTime);
            }
            _item_1.mStartTime = RoomConverter.toCalendar(_tmp);
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfMEndTime);
            }
            _item_1.mEndTime = RoomConverter.toCalendar(_tmp_1);
            _item_1.mName = _cursor.getString(_cursorIndexOfMName);
            _item_1.mLocation = _cursor.getString(_cursorIndexOfMLocation);
            _item_1.mColor = _cursor.getInt(_cursorIndexOfMColor);
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMAllDay);
            _item_1.mAllDay = _tmp_2 != 0;
            final Calendar _tmpDateIfTemporary;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfDateIfTemporary)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfDateIfTemporary);
            }
            _tmpDateIfTemporary = RoomConverter.toCalendar(_tmp_3);
            _item_1.setDateIfTemporary(_tmpDateIfTemporary);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item_1.setType(_tmpType);
            final boolean _tmpTriggerAlarm;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfTriggerAlarm);
            _tmpTriggerAlarm = _tmp_4 != 0;
            _item_1.setTriggerAlarm(_tmpTriggerAlarm);
            final boolean _tmpIsPermanent;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsPermanent);
            _tmpIsPermanent = _tmp_5 != 0;
            _item_1.setPermanent(_tmpIsPermanent);
            final String _tmpDetails;
            _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            _item_1.setDetails(_tmpDetails);
            final List<Integer> _tmpSelectedDayList;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfSelectedDayList);
            _tmpSelectedDayList = RoomConverter.fromStringToIntList(_tmp_6);
            _item_1.setSelectedDayList(_tmpSelectedDayList);
            _result.add(_item_1);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  RoutineItem getRoutineByID(long routineID) {
    final String _sql = "SELECT * FROM RoutineItem WHERE id = (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, routineID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMStartTime = _cursor.getColumnIndexOrThrow("mStartTime");
      final int _cursorIndexOfMEndTime = _cursor.getColumnIndexOrThrow("mEndTime");
      final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("mName");
      final int _cursorIndexOfMLocation = _cursor.getColumnIndexOrThrow("mLocation");
      final int _cursorIndexOfMColor = _cursor.getColumnIndexOrThrow("mColor");
      final int _cursorIndexOfMAllDay = _cursor.getColumnIndexOrThrow("mAllDay");
      final int _cursorIndexOfDateIfTemporary = _cursor.getColumnIndexOrThrow("dateIfTemporary");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final int _cursorIndexOfTriggerAlarm = _cursor.getColumnIndexOrThrow("triggerAlarm");
      final int _cursorIndexOfIsPermanent = _cursor.getColumnIndexOrThrow("isPermanent");
      final int _cursorIndexOfDetails = _cursor.getColumnIndexOrThrow("details");
      final int _cursorIndexOfSelectedDayList = _cursor.getColumnIndexOrThrow("selectedDayList");
      final RoutineItem _result;
      if(_cursor.moveToFirst()) {
        _result = new RoutineItem();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfMStartTime)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfMStartTime);
        }
        _result.mStartTime = RoomConverter.toCalendar(_tmp);
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMEndTime)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfMEndTime);
        }
        _result.mEndTime = RoomConverter.toCalendar(_tmp_1);
        _result.mName = _cursor.getString(_cursorIndexOfMName);
        _result.mLocation = _cursor.getString(_cursorIndexOfMLocation);
        _result.mColor = _cursor.getInt(_cursorIndexOfMColor);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfMAllDay);
        _result.mAllDay = _tmp_2 != 0;
        final Calendar _tmpDateIfTemporary;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfDateIfTemporary)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfDateIfTemporary);
        }
        _tmpDateIfTemporary = RoomConverter.toCalendar(_tmp_3);
        _result.setDateIfTemporary(_tmpDateIfTemporary);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        _result.setType(_tmpType);
        final boolean _tmpTriggerAlarm;
        final int _tmp_4;
        _tmp_4 = _cursor.getInt(_cursorIndexOfTriggerAlarm);
        _tmpTriggerAlarm = _tmp_4 != 0;
        _result.setTriggerAlarm(_tmpTriggerAlarm);
        final boolean _tmpIsPermanent;
        final int _tmp_5;
        _tmp_5 = _cursor.getInt(_cursorIndexOfIsPermanent);
        _tmpIsPermanent = _tmp_5 != 0;
        _result.setPermanent(_tmpIsPermanent);
        final String _tmpDetails;
        _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
        _result.setDetails(_tmpDetails);
        final List<Integer> _tmpSelectedDayList;
        final String _tmp_6;
        _tmp_6 = _cursor.getString(_cursorIndexOfSelectedDayList);
        _tmpSelectedDayList = RoomConverter.fromStringToIntList(_tmp_6);
        _result.setSelectedDayList(_tmpSelectedDayList);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  LiveData<List<RoutineItem>> getAllLiveRoutines(String type) {
    final String _sql = "SELECT * FROM RoutineItem WHERE type = (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return new ComputableLiveData<List<RoutineItem>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<RoutineItem> compute() {
        if (_observer == null) {
          _observer = new Observer("RoutineItem") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMStartTime = _cursor.getColumnIndexOrThrow("mStartTime");
          final int _cursorIndexOfMEndTime = _cursor.getColumnIndexOrThrow("mEndTime");
          final int _cursorIndexOfMName = _cursor.getColumnIndexOrThrow("mName");
          final int _cursorIndexOfMLocation = _cursor.getColumnIndexOrThrow("mLocation");
          final int _cursorIndexOfMColor = _cursor.getColumnIndexOrThrow("mColor");
          final int _cursorIndexOfMAllDay = _cursor.getColumnIndexOrThrow("mAllDay");
          final int _cursorIndexOfDateIfTemporary = _cursor.getColumnIndexOrThrow("dateIfTemporary");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final int _cursorIndexOfTriggerAlarm = _cursor.getColumnIndexOrThrow("triggerAlarm");
          final int _cursorIndexOfIsPermanent = _cursor.getColumnIndexOrThrow("isPermanent");
          final int _cursorIndexOfDetails = _cursor.getColumnIndexOrThrow("details");
          final int _cursorIndexOfSelectedDayList = _cursor.getColumnIndexOrThrow("selectedDayList");
          final List<RoutineItem> _result = new ArrayList<RoutineItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RoutineItem _item;
            _item = new RoutineItem();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfMStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfMStartTime);
            }
            _item.mStartTime = RoomConverter.toCalendar(_tmp);
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfMEndTime);
            }
            _item.mEndTime = RoomConverter.toCalendar(_tmp_1);
            _item.mName = _cursor.getString(_cursorIndexOfMName);
            _item.mLocation = _cursor.getString(_cursorIndexOfMLocation);
            _item.mColor = _cursor.getInt(_cursorIndexOfMColor);
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMAllDay);
            _item.mAllDay = _tmp_2 != 0;
            final Calendar _tmpDateIfTemporary;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfDateIfTemporary)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfDateIfTemporary);
            }
            _tmpDateIfTemporary = RoomConverter.toCalendar(_tmp_3);
            _item.setDateIfTemporary(_tmpDateIfTemporary);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item.setType(_tmpType);
            final boolean _tmpTriggerAlarm;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfTriggerAlarm);
            _tmpTriggerAlarm = _tmp_4 != 0;
            _item.setTriggerAlarm(_tmpTriggerAlarm);
            final boolean _tmpIsPermanent;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsPermanent);
            _tmpIsPermanent = _tmp_5 != 0;
            _item.setPermanent(_tmpIsPermanent);
            final String _tmpDetails;
            _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            _item.setDetails(_tmpDetails);
            final List<Integer> _tmpSelectedDayList;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfSelectedDayList);
            _tmpSelectedDayList = RoomConverter.fromStringToIntList(_tmp_6);
            _item.setSelectedDayList(_tmpSelectedDayList);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
