package com.example.quotely.interfaces;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.quotely.models.Quote;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuoteDao_Impl implements QuoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Quote> __insertionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Quote> __deletionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Quote> __updateAdapterOfQuote;

  public QuoteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuote = new EntityInsertionAdapter<Quote>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `quotes` (`id`,`author-name`,`quote-text`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Quote value) {
        stmt.bindLong(1, value.id);
        if (value.author == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.author);
        }
        if (value.text == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.text);
        }
      }
    };
    this.__deletionAdapterOfQuote = new EntityDeletionOrUpdateAdapter<Quote>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `quotes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Quote value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfQuote = new EntityDeletionOrUpdateAdapter<Quote>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `quotes` SET `id` = ?,`author-name` = ?,`quote-text` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Quote value) {
        stmt.bindLong(1, value.id);
        if (value.author == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.author);
        }
        if (value.text == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.text);
        }
        stmt.bindLong(4, value.id);
      }
    };
  }

  @Override
  public void insert(final Quote quote) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfQuote.insert(quote);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Quote quote) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfQuote.handle(quote);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Quote quote) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfQuote.handle(quote);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Quote> getAll() {
    final String _sql = "SELECT * FROM quotes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author-name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "quote-text");
      final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Quote _item;
        _item = new Quote();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfAuthor)) {
          _item.author = null;
        } else {
          _item.author = _cursor.getString(_cursorIndexOfAuthor);
        }
        if (_cursor.isNull(_cursorIndexOfText)) {
          _item.text = null;
        } else {
          _item.text = _cursor.getString(_cursorIndexOfText);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Quote> loadByText(final String text) {
    final String _sql = "SELECT * FROM quotes WHERE `quote-text` LIKE '% ' || ? || ' %'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (text == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, text);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author-name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "quote-text");
      final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Quote _item;
        _item = new Quote();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfAuthor)) {
          _item.author = null;
        } else {
          _item.author = _cursor.getString(_cursorIndexOfAuthor);
        }
        if (_cursor.isNull(_cursorIndexOfText)) {
          _item.text = null;
        } else {
          _item.text = _cursor.getString(_cursorIndexOfText);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Quote> loadByAuthor(final String text) {
    final String _sql = "SELECT * FROM quotes WHERE `author-name` LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (text == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, text);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author-name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "quote-text");
      final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Quote _item;
        _item = new Quote();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfAuthor)) {
          _item.author = null;
        } else {
          _item.author = _cursor.getString(_cursorIndexOfAuthor);
        }
        if (_cursor.isNull(_cursorIndexOfText)) {
          _item.text = null;
        } else {
          _item.text = _cursor.getString(_cursorIndexOfText);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
