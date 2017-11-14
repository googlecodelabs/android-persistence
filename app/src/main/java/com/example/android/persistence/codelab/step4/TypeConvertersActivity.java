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

package com.example.android.persistence.codelab.step4;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.codelabs.persistence.R;
import com.example.android.persistence.codelab.db.Book;

import java.util.List;

public class TypeConvertersActivity extends AppCompatActivity {

    private TypeConvertersViewModel mViewModel;

    private TextView mBooksTextView;

    private static void showBooksInUi(final @NonNull List<Book> books,
                                      final TextView booksTextView) {
        StringBuilder sb = new StringBuilder();

        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");

        }
        booksTextView.setText(sb.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_activity);
        mBooksTextView = findViewById(R.id.books_tv);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(TypeConvertersViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiBooks();
    }

    public void onRefreshBtClicked(View view) {
        mViewModel.createDb();
    }

    private void subscribeUiBooks() {
        mViewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@NonNull final List<Book> books) {
                showBooksInUi(books, mBooksTextView);
            }
        });
    }
}
