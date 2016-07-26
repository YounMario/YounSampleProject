package com.younchen.younsampleproject.sys.pic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.SparseArray;

import com.younchen.younsampleproject.sys.pic.bean.Book;

public class AidlSampleService extends Service {

    private IBookManagerInterface bookManager;

    public AidlSampleService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        bookManager = new BookManagerImpl();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bookManager.asBinder();
    }

    class BookManagerImpl extends AbstractBookManager {

        private SparseArray<Book> list;

        public BookManagerImpl() {
            list = new SparseArray<>();
        }

        @Override
        public void addBook(Book book) {
            if (book != null) {
                list.put(book.getId(),book);
            }
        }

        @Override
        public Book querybook(int id) {
            return list.get(id);
        }
    }
}
