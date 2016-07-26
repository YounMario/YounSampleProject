package com.younchen.younsampleproject.sys.pic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.younchen.younsampleproject.sys.pic.bean.Book;


/**
 * Created by 龙泉 on 2016/7/25.
 */
public abstract class AbstractBookManager extends Binder implements IBookManagerInterface {


    public static IBookManagerInterface asInterface(IBinder binder) {
        if (binder == null) return null;
        IInterface target = binder.queryLocalInterface(IBookManagerInterface.DESCRIPTOR);
        if (target != null && target instanceof IBookManagerInterface) {
            return (IBookManagerInterface) target;
        }
        return new Proxy(binder);
    }



    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case TRANSACTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if (data.readInt() != 0) {
                    book = Book.CREATOR.createFromParcel(data);
                } else {
                    book = null;
                }
                this.addBook(book);
                reply.writeNoException();
                return true;
            case TRANSACTION_querybook:
                data.enforceInterface(DESCRIPTOR);
                int id = data.readInt();
                Book result = querybook(id);
                reply.writeNoException();
                if ((result != null)) {
                    reply.writeInt(1);
                    result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                } else {
                    reply.writeInt(0);
                }
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    private static class Proxy implements IBookManagerInterface {

        private IBinder binder;

        public Proxy(IBinder binder) {
            this.binder = binder;
        }

        @Override
        public void addBook(Book book) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book != null) {
                    data.writeInt(1);
                    book.writeToParcel(data, 0);
                } else {
                    data.writeInt(0);
                }
                binder.transact(TRANSACTION_addBook, data, reply, 0);
                reply.readException();
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override
        public Book querybook(int id) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            Book book;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                data.writeInt(id);
                binder.transact(TRANSACTION_querybook, data, reply, 0);
                reply.readException();
                if (reply.readInt() != 0) {
                    book = Book.CREATOR.createFromParcel(reply);
                } else {
                    book = null;
                }
                return book;
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                reply.recycle();
                data.recycle();
            }
            return null;
        }

        @Override
        public IBinder asBinder() {
            return binder;
        }
    }
}
