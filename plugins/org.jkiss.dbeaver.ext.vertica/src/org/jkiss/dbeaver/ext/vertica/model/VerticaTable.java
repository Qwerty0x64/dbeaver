/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2021 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.vertica.model;

import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.ext.generic.model.GenericTable;
import org.jkiss.dbeaver.model.DBPObjectStatistics;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCResultSet;
import org.jkiss.dbeaver.model.preferences.DBPPropertySource;

import java.sql.SQLException;

/**
 * VerticaTable
 */
public class VerticaTable extends GenericTable implements DBPObjectStatistics
{
    public static final String TABLE_TYPE_FLEX = "FLEXTABLE";
    private long tableSize = -1;

    public VerticaTable(VerticaSchema container, String tableName, String tableType, JDBCResultSet dbResult) {
        super(container, tableName, tableType, dbResult);
    }

    @Override
    public boolean isPhysicalTable() {
        return !isView() && !isFlexTable();
    }

    public boolean isFlexTable() {
        return ((VerticaSchema)getContainer()).isFlexTableName(getName());
    }

    @Override
    public boolean hasStatistics() {
        return tableSize != -1;
    }

    @Override
    public long getStatObjectSize() {
        return tableSize;
    }

    void fetchStatistics(JDBCResultSet dbResult) throws SQLException {
        tableSize = dbResult.getLong("used_bytes");
    }

    @Nullable
    @Override
    public DBPPropertySource getStatProperties() {
        return null;
    }
}
