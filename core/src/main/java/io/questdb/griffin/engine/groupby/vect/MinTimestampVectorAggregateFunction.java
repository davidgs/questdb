/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2020 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.griffin.engine.groupby.vect;

import io.questdb.cairo.sql.Record;
import io.questdb.griffin.engine.functions.TimestampFunction;
import io.questdb.std.Numbers;
import io.questdb.std.Vect;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

public class MinTimestampVectorAggregateFunction extends TimestampFunction implements VectorAggregateFunction {

    public static final LongBinaryOperator MIN = Math::min;
    private final LongAccumulator accumulator = new LongAccumulator(
            MIN, Long.MAX_VALUE
    );
    private final int columnIndex;

    public MinTimestampVectorAggregateFunction(int position, int columnIndex) {
        super(position);
        this.columnIndex = columnIndex;
    }

    @Override
    public void aggregate(long address, long count, int workerId) {
        if (address != 0) {
            final long value = Vect.minLong(address, count);
            if (value != Numbers.LONG_NaN) {
                accumulator.accumulate(value);
            }
        }
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public void clear() {
        accumulator.reset();
    }

    @Override
    public long getTimestamp(Record rec) {
        final long value = accumulator.longValue();
        return value == Long.MAX_VALUE ? Numbers.LONG_NaN : value;
    }
}
