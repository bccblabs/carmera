package carmera.io.carmera.utils;

/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    public static String joinStrings (List<String> strings, String delimiter) {
        String result = "";
        for (String x:strings) {
            result += x + delimiter;
        }
        return result.substring(0, result.length()-2);
    }

    public static List<KeyPairBoolData> getSpinnerValues (List<String> values, boolean sort) {
        if (sort)
            Collections.sort(values);
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<values.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i+1);
            h.setName(values.get(i));
            h.setSelected(false);
            kv_list.add(h);
        }
        return kv_list;
    }

    public static List<KeyPairBoolData> getIntegerSpinnerValues (List<Integer> values) {
        Collections.sort(values);
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<values.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i+1);
            h.setName(values.get(i).toString());
            h.setSelected(false);
            kv_list.add(h);
        }
        return kv_list;
    }

    public static void setText (TextView textview, String text) {
        if (text != null && text.length() > 1)
            textview.setText(text);
        else
            textview.setVisibility(View.GONE);
    }



    public static  List<KeyPairBoolData> getSelectedValues (List<String> values) {
        List<KeyPairBoolData> items = Util.getSpinnerValues(values, true);
        for (KeyPairBoolData item : items) {
            item.setSelected(true);
        }
        return items;
    }

    public static List<KeyPairBoolData> getIntSelectedValues (List<Integer> values) {
        List<KeyPairBoolData> items = Util.getIntegerSpinnerValues(values);
        for (KeyPairBoolData item : items) {
            item.setSelected(true);
        }
        return items;
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
