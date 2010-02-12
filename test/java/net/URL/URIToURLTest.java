/*
 * Copyright 2001 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/* @test
 * @bug 4482455
 * @summary URI.toURL() implementation needs to be improved
 *
 */

import java.net.*;

public class URIToURLTest {
    public static void main(String args[]) throws Exception {
        String[] uris = {
           "http://jag:cafebabe@java.sun.com:94/b/c/d?q#g",
           "http://[1080:0:0:0:8:800:200C:417A]/index.html",
           "http://a/b/c/d;p?q",
           "ftp://ftp.is.co.za/rfc/rfc1808.txt",
           "mailto:mduerst@ifi.unizh.ch", // opaque url
           "http:comp.infosystems.www.servers.unix" //opaque url
        };

        for (int i = 0; i < uris.length; i++) {
            URI uri = new URI(uris[i]);
            URL url = uri.toURL();
            String scheme = uri.getScheme();
            boolean schemeCheck = scheme == null? url.getProtocol() == null :
                scheme.equals(url.getProtocol());
            if (!schemeCheck)
                throw new RuntimeException("uri.scheme is " + scheme +
                                           " url.protocol is " +
                                           url.getProtocol());

            if (uri.isOpaque()) {
                String ssp = uri.getSchemeSpecificPart();
                boolean sspCheck = ssp == null? uri.getPath() == null :
                    ssp.equals(url.getPath());
                if (!sspCheck) {
                    throw new RuntimeException("uri.ssp is " + ssp +
                                           " url.path is " + url.getPath());
                }
            } else {
                String authority = uri.getAuthority();
                boolean authorityCheck = authority == null?
                    url.getAuthority() == null :
                    authority.equals(url.getAuthority());
                if (!authorityCheck) {
                    throw new RuntimeException("uri.authority is " +
                                               authority + " url's is " +
                                               url.getAuthority());
                }
                String host = uri.getHost();
                boolean hostCheck = host == null ? url.getHost() == null :
                    host.equals(url.getHost());
                if (!hostCheck)
                    throw new RuntimeException("uri.host is " +
                                               host + " url's is " +
                                               url.getHost());
                if (host != null) {
                    String userInfo = uri.getUserInfo();
                    boolean userInfoCheck = userInfo == null?
                        url.getUserInfo() == null :
                        userInfo.equals(url.getUserInfo());
                    if (uri.getPort() != url.getPort())
                        throw new RuntimeException("uri.port is " +
                                               uri.getPort() + " url's is " +
                                               url.getPort());
                }

                String path = uri.getPath();
                boolean pathCheck = path == null? url.getPath() == null :
                    path.equals(url.getPath());
                if (!pathCheck)
                    throw new RuntimeException("uri.path is " + path +
                                               " url.path is " +
                                               url.getPath());
                String query = uri.getQuery();
                boolean queryCheck = query == null? url.getQuery() == null :
                    query.equals(url.getQuery());
                if (!queryCheck)
                    throw new RuntimeException("uri.query is " + query +
                                               " url.query is " +
                                               url.getQuery());
            }
            String frag = uri.getFragment();
            boolean fragCheck = frag == null? url.getRef() == null :
            frag.equals(url.getRef());
            if (!fragCheck)
                    throw new RuntimeException("uri.frag is " + frag +
                                               " url.ref is " +
                                               url.getRef());
        }
    }
}
