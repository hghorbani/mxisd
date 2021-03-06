/*
 * mxisd - Matrix Identity Server Daemon
 * Copyright (C) 2018 Kamax Sarl
 *
 * https://www.kamax.io/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.kamax.mxisd.http.undertow.handler.identity.v1;

import io.kamax.matrix.crypto.KeyManager;
import io.kamax.mxisd.http.IsAPIv1;
import io.undertow.server.HttpServerExchange;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegularKeyIsValidHandler extends KeyIsValidHandler {

    public static final String Path = IsAPIv1.Base + "/pubkey/isvalid";

    private transient final Logger log = LoggerFactory.getLogger(RegularKeyIsValidHandler.class);

    private KeyManager mgr;

    public RegularKeyIsValidHandler(KeyManager mgr) {
        this.mgr = mgr;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        String pubKey = getQueryParameter(exchange, "public_key");
        log.info("Validating public key {}", pubKey);

        // TODO do in manager
        boolean valid = StringUtils.equals(pubKey, mgr.getPublicKeyBase64(mgr.getCurrentIndex()));
        respondJson(exchange, valid ? validKey : invalidKey);
    }

}
