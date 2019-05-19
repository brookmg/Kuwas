/*
 *           Copyright (c) 2019 Brook Mezgebu
 *           Permission is hereby granted, free of charge, to any person obtaining
 *           a copy of this software and associated documentation files (the
 *           "Software"), to deal in the Software without restriction, including
 *           without limitation the rights to use, copy, modify, merge, publish,
 *           distribute, sublicense, and/or sell copies of the Software, and to
 *           permit persons to whom the Software is furnished to do so, subject to
 *           the following conditions:
 *
 *           The above copyright notice and this permission notice shall be
 *           included in all copies or substantial portions of the Software.
 *
 *           THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *           EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *           MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *           NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *           LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *           OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *           WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package app.kuwas.android.utils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import app.kuwas.android.ui.fragments.BaseFragment;

/**
 * Created by BrookMG on 9/9/2018 in app.azemari.android.fragments
 * inside the project EthioMusic .
 */
public class FragmentHelper {

    private final ArrayList<SoftReference<BaseFragment>> fragmentsQueue = new ArrayList<>();

    public FragmentHelper() {

    }

    public BaseFragment getItemFromQueue (int position) {
        if (queueSize() > position)
            return fragmentsQueue.get(position).get();
        return null;
    }

    public int queueSize () {
        return fragmentsQueue.size();
    }

    public void addFragmentToQueue (BaseFragment fragment) {
        fragmentsQueue.add(new SoftReference<>(fragment));
    }

    public void removeLastFragment () {
        fragmentsQueue.remove(fragmentsQueue.size() - 1);
    }

    public boolean removeFragment (BaseFragment fragment) {

        for (SoftReference<BaseFragment> i : fragmentsQueue) {
            if (i.get().equals(fragment)) {
                fragmentsQueue.remove(i);
                return true;
            }
        }

        return false;
    }

}
