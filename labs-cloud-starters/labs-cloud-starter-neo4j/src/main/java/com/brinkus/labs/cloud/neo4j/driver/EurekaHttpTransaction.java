/*
 * Labs Cloud Starter Service
 * Copyright (C) 2016  Balazs Brinkus
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brinkus.labs.cloud.neo4j.driver;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.neo4j.ogm.exception.TransactionException;
import org.neo4j.ogm.transaction.AbstractTransaction;
import org.neo4j.ogm.transaction.TransactionManager;

class EurekaHttpTransaction extends AbstractTransaction {

    private final EurekaHttpClient client;

    private final String url;

    public EurekaHttpTransaction(TransactionManager transactionManager, EurekaHttpDriver driver, String url) {
    public EurekaHttpTransaction(TransactionManager transactionManager, EurekaHttpClient client, String url) {
        super(transactionManager);
        this.client = client;
        this.url = url;
    }

    @Override
    public void rollback() {
        try {
            if (transactionManager.canRollback()) {
                HttpDelete request = new HttpDelete(url);
                client.executeHttpRequest(request);
            }
        } catch (Exception e) {
            throw new TransactionException(e.getLocalizedMessage());
        } finally {
            super.rollback(); // must always be done to keep extension depth correct
        }
    }

    @Override
    public void commit() {
        try {
            if (transactionManager.canCommit()) {
                HttpPost request = new HttpPost(url + "/commit");
                request.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                client.executeHttpRequest(request);
            }
        } catch (Exception e) {
            throw new TransactionException(e.getLocalizedMessage());
        } finally {
            super.commit(); // must always be done to keep extension depth correct
        }
    }

}
