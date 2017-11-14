/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.codelab.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
@TypeConverters(DateConverter.class)
public interface BookDao {

    @Query("select * from Book where id = :id")
    Book loadBookById(int id);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName"
    )
    LiveData<List<Book>> findBooksBorrowedByName(String userName);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName " +
            "AND Loan.endTime > :after "
    )
    LiveData<List<Book>> findBooksBorrowedByNameAfter(String userName, Date after);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id = Book.id " +
            "INNER JOIN User on User.id = Loan.user_id " +
            "WHERE User.name LIKE :userName"
    )
    List<Book> findBooksBorrowedByNameSync(String userName);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId "
    )
    LiveData<List<Book>> findBooksBorrowedByUser(String userId);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId " +
            "AND Loan.endTime > :after "
    )
    LiveData<List<Book>> findBooksBorrowedByUserAfter(String userId, Date after);

    @Query("SELECT * FROM Book " +
            "INNER JOIN Loan ON Loan.book_id LIKE Book.id " +
            "WHERE Loan.user_id LIKE :userId "
    )
    List<Book> findBooksBorrowedByUserSync(String userId);

    @Query("SELECT * FROM Book")
    LiveData<List<Book>> findAllBooks();


    @Query("SELECT * FROM Book")
    List<Book> findAllBooksSync();

    @Insert(onConflict = IGNORE)
    void insertBook(Book book);

    @Update(onConflict = REPLACE)
    void updateBook(Book book);

    @Query("DELETE FROM Book")
    void deleteAll();
}
