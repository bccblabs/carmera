package veme.cario.com.CARmera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import
/**
 * Created by bski on 11/5/14.
 */
public class BaseActivity extends Activity {

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
    }

    @Override
    public void onResume () {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_capture: {
                if (!(this instanceof CaptureActivity)) {
                    Intent i = new Intent(this, CaptureActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }
            case R.id.action_favorites: {
                if (!(this instanceof FavoritesActivity)) {
                    Intent i = new Intent(this, FavoritesActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }

            case R.id.activity_tagged_photo: {
                if (!(this instanceof TaggedPhotoActivity)) {
                    Intent i = new Intent(this, TaggedPhotoActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
