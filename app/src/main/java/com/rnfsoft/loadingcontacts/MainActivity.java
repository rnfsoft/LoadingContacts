package com.rnfsoft.loadingcontacts;



import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends FragmentActivity {
    private SimpleCursorAdapter adapter;
    private static final int CONTACT_LOADER_ID = 76;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupCursorAdapter();
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID, new Bundle(),  contactsLoader);
        ListView lvContacts = (ListView)findViewById(R.id.lvContacts);
        lvContacts.setAdapter(adapter);
    }

    private void setupCursorAdapter() {
        String[] uiBindFrom = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_URI};
        int[] uiBindTo = {R.id.tvName, R.id.ivImage};
        adapter = new SimpleCursorAdapter(this, R.layout.item_contact, null, uiBindFrom, uiBindTo,0);

    }

    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] projectionsFields = new String[]{ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI};

            CursorLoader cursorLoader = new CursorLoader(MainActivity.this, ContactsContract.Contacts.CONTENT_URI, projectionsFields, null, null, null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
            adapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    };
}
