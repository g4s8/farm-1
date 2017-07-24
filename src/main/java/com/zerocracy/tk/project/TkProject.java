/**
 * Copyright (c) 2016-2017 Zerocracy
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
package com.zerocracy.tk.project;

import com.zerocracy.jstk.Farm;
import com.zerocracy.jstk.Project;
import com.zerocracy.tk.RsPage;
import java.io.IOException;
import java.util.Properties;
import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.xe.XeAppend;

/**
 * Project page.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TkProject implements TkRegex {

    /**
     * Properties.
     */
    private final Properties props;

    /**
     * Farm.
     */
    private final Farm farm;

    /**
     * Ctor.
     * @param pps Properties
     * @param frm Farm
     */
    public TkProject(final Properties pps, final Farm frm) {
        this.props = pps;
        this.farm = frm;
    }

    @Override
    public Response act(final RqRegex req) throws IOException {
        final Project project = new RqProject(this.farm, req).value();
        return new RsPage(
            this.props,
            "/xsl/project.xsl",
            req,
            () -> new XeAppend(
                "project",
                project.toString()
            )
        );
    }

}
