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

package io.questdb.griffin;

import io.questdb.griffin.engine.functions.rnd.SharedRandom;
import io.questdb.std.Rnd;
import org.junit.Before;
import org.junit.Test;

public class TimestampQueryTest extends AbstractGriffinTest {

    @Before
    public void setUp3() {
        SharedRandom.RANDOM.set(new Rnd());
    }

    @Test
    public void testEqualityTimestampFormatYearOnlyPositiveTest() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts ='2020'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp ='2020'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }


    @Test
    public void testEqualityTimestampFormatYearOnlyNegativeTest() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts ='2021'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp ='2021'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testEqualityTimestampFormatYearAndMonthPositiveTest() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts ='2020-12'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp ='2020-12'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testEqualityTimestampFormatYearAndMonthNegativeTest() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts ='2021-01'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp ='2021-01'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts ='2020-11'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp ='2020-11'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testLessThanTimestampFormatYearOnlyPositiveTest1() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts <'2021'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp <'2021'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testLessThanTimestampFormatYearOnlyPositiveTest2() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where '2019' < ts'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where '2019' <  timestamp";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testLessThanTimestampFormatYearOnlyNegativeTest1() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts <'2020'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp <'2020'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testLessThanTimestampFormatYearOnlyNegativeTest2() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where '2021' < ts'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where '2021' <  timestamp";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }


    @Test
    public void testLMoreThanTimestampFormatYearOnlyPositiveTest1() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts > '2019'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp > '2019'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testMoreThanTimestampFormatYearOnlyPositiveTest2() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where '2021' > ts'
            expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            query = "SELECT * FROM ob_mem_snapshot where '2021' >  timestamp";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testMoreThanTimestampFormatYearOnlyNegativeTest1() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where ts <'2021'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where timestamp <'2020'";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }

    @Test
    public void testMoreThanTimestampFormatYearOnlyNegativeTest2() throws Exception {
        assertMemoryLeak(() -> {
            //create table
            String createStmt = "create table ob_mem_snapshot (symbol int,  me_seq_num long,  timestamp timestamp) timestamp(timestamp) partition by DAY";
            compiler.compile(createStmt, sqlExecutionContext);
            //insert
            executeInsert("INSERT INTO ob_mem_snapshot  VALUES(1, 1, 1609459199000000)");
            String expected = "symbol\tme_seq_num\ttimestamp\n" +
                    "1\t1\t2020-12-31T23:59:59.000000Z\n";
            String query = "select * from ob_mem_snapshot";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
            // test where '2020' < ts'
            expected = "symbol\tme_seq_num\ttimestamp\n";
            query = "SELECT * FROM ob_mem_snapshot where '2021' <  timestamp";
            printSqlResult(expected, query, "timestamp", null, null, true, true);
        });
    }
}
