/*******************************************************************************
 * ___                  _   ____  ____
 * / _ \ _   _  ___  ___| |_|  _ \| __ )
 * | | | | | | |/ _ \/ __| __| | | |  _ \
 * | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 * \__\_\\__,_|\___||___/\__|____/|____/
 * <p>
 * Copyright (C) 2014-2016 Appsicle
 * <p>
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * As a special exception, the copyright holders give permission to link the
 * code of portions of this program with the OpenSSL library under certain
 * conditions as described in each individual source file and distribute
 * linked combinations including the program with the OpenSSL library. You
 * must comply with the GNU Affero General Public License in all respects for
 * all of the code used other than as permitted herein. If you modify file(s)
 * with this exception, you may extend this exception to your version of the
 * file(s), but you are not obligated to do so. If you do not wish to do so,
 * delete this exception statement from your version. If you delete this
 * exception statement from all source files in the program, then also delete
 * it in the license file.
 ******************************************************************************/

package com.questdb.ql.impl.join;

import com.questdb.factory.JournalReaderFactory;
import com.questdb.factory.configuration.RecordMetadata;
import com.questdb.ql.StorageFacade;
import com.questdb.store.SymbolTable;

public class SplitRecordStorageFacade implements StorageFacade {
    private final RecordMetadata metadata;
    private final int split;
    private JournalReaderFactory factory;
    private StorageFacade a;
    private StorageFacade b;

    public SplitRecordStorageFacade(RecordMetadata metadata, int split) {
        this.metadata = metadata;
        this.split = split;
    }

    @Override
    public JournalReaderFactory getFactory() {
        return factory;
    }

    @Override
    public SymbolTable getSymbolTable(int index) {
        return index < split ? a.getSymbolTable(index) : b.getSymbolTable(index - split);
    }

    @Override
    public SymbolTable getSymbolTable(String name) {
        return getSymbolTable(metadata.getColumnIndex(name));
    }

    public void prepare(JournalReaderFactory factory, StorageFacade a, StorageFacade b) {
        this.factory = factory;
        this.a = a;
        this.b = b;
    }
}