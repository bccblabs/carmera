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

import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
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

    public static List<KeyPairBoolData> getSpinnerValues (List<String> values, boolean sort, List<String> selectedValues) {
        if (sort)
            Collections.sort(values);
        final List<KeyPairBoolData> kv_list = new ArrayList<KeyPairBoolData>();
        for(int i=0; i<values.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(values.get(i));
            if (selectedValues.indexOf(values.get(i).toLowerCase()) > -1)
                h.setSelected(true);
            else
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


    public static String formatCurrency (Number i) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return fmt.format(i.intValue());
    }

    public static <E> void addAllIfNotNull(List<E> list, Collection<? extends E> c) {
        if (c != null) {
            list.addAll(c);
        }
    }

    public static void addIfNotZero (List<String> list, Integer value, String unit) {
        if (value > 0)
            list.add(value.toString() + "+" + unit);
    }

}
