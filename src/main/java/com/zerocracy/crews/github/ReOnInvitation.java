/**
 * Copyright (c) 2016 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.crews.github;

import com.jcabi.github.Github;
import com.jcabi.github.RtPagination;
import com.jcabi.http.Request;
import com.jcabi.http.response.RestResponse;
import com.jcabi.log.Logger;
import com.zerocracy.jstk.Farm;
import java.io.IOException;
import java.net.HttpURLConnection;
import javax.json.JsonObject;

/**
 * On GitHub invitations.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.2
 */
final class ReOnInvitation implements Reaction {

    /**
     * Github client.
     */
    private final Github github;

    /**
     * Ctor.
     * @param ghb Github client
     */
    ReOnInvitation(final Github ghb) {
        this.github = ghb;
    }

    @Override
    public void react(final Farm farm, final JsonObject event)
        throws IOException {
        final Request entry = this.github.entry().reset("Accept").header(
            "accept", "application/vnd.github.swamp-thing-preview+json"
        );
        final Iterable<JsonObject> all = new RtPagination<>(
            entry.uri().path("/user/repository_invitations").back(),
            RtPagination.COPYING
        );
        for (final JsonObject json : all) {
            entry.uri().path("/user/repository_invitations/")
                .path(Integer.toString(json.getInt("id"))).back()
                .method(Request.PATCH)
                .fetch()
                .as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_NO_CONTENT);
            Logger.info(
                this, "invitation to %s accepted",
                json.getJsonObject("repository").getString("full_name")
            );
        }
    }
}