/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.soccerethiopia.network;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by BrookMG on 1/13/2019 in io.brookmg.soccerethiopiaapi.network
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings({"unused" , "WeakerAccess"})
public class CachedStringRequest extends StringRequest {

    private long cacheHitButRefreshed = 3 * 60 * 1000;
    private long cacheExpired = 24 * 60 * 60 * 1000;


    public CachedStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CachedStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener, long cacheHitButRefreshed) {
        super(method, url, listener, errorListener);
        this.cacheHitButRefreshed = cacheHitButRefreshed;
    }

    public CachedStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener, long cacheHitButRefreshed, long cacheExpired) {
        super(method, url, listener, errorListener);
        this.cacheExpired = cacheExpired;
        this.cacheHitButRefreshed = cacheHitButRefreshed;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            Cache.Entry entry = (HttpHeaderParser.parseCacheHeaders(response) == null) ? new Cache.Entry() : HttpHeaderParser.parseCacheHeaders(response);
            long now = System.currentTimeMillis();

            final long softExpired = now + cacheHitButRefreshed;
            final long ttl = now + cacheExpired;

            entry.softTtl = softExpired;
            entry.ttl = ttl;
            entry.data = response.data;

            String header = response.headers.get("Date");
            if (header != null) entry.serverDate = HttpHeaderParser.parseDateAsEpoch(header);

            header = response.headers.get("Last-Modified");
            if (header != null) entry.lastModified = HttpHeaderParser.parseDateAsEpoch(header);

            entry.responseHeaders = response.headers;
            return Response.success(new String(response.data , HttpHeaderParser.parseCharset(response.headers)) , entry);

        } catch (UnsupportedEncodingException encodingEx) {
           return Response.error(new ParseError(encodingEx));
        }
    }
}
