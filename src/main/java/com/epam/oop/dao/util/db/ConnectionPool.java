package com.epam.oop.dao.util.db;

import com.epam.oop.dao.util.db.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * .
 *
 * @author Uladzislau Seuruk.
 */
public final class ConnectionPool {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final int DEFAULT_POOL_SIZE = 5;

    private static ConnectionPool instance = new ConnectionPool();

    private BlockingQueue<Connection> busyConnectionQueue;
    private BlockingQueue<Connection> connectionQueue;

    private String driverName;
    private String password;
    private String url;
    private String user;
    private int poolSize;

    private ConnectionPool() {
        DbResourceManager manager = DbResourceManager.getInstance();
        driverName = manager.getValue(DbParameter.DB_DRIVER);
        password = manager.getValue(DbParameter.DB_PASSWORD);
        url = manager.getValue(DbParameter.DB_URL);
        user = manager.getValue(DbParameter.DB_USER);
        try {
            poolSize = Integer.parseInt(manager.getValue(DbParameter.DB_POOL_SIZE));
        } catch (NumberFormatException nfe) {
            poolSize = DEFAULT_POOL_SIZE;
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("Connection wasn't returned to the pool.", e);
        }
    }

    public void closeConnection(Connection connection, Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            LOG.error("Statement wasn't closed.", e);
        }
        closeConnection(connection);
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet set) {
        try {
            set.close();
        } catch (SQLException e) {
            LOG.error("Result set wasn't closed.", e);
        }
        closeConnection(connection, statement);
    }

    public void dispose() {
        try {
            closeConnectionQueue(busyConnectionQueue);
            closeConnectionQueue(connectionQueue);
        } catch (SQLException e) {
            LOG.error("Error during closing connection.", e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = connectionQueue.take();
            busyConnectionQueue.add(connection);
            return connection;
        } catch (InterruptedException ie) {
            throw new ConnectionPoolException("Error connecting to data source.", ie);
        }
    }

    public void initPoolData() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            //Class.forName(driverName);
            busyConnectionQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; ++i) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {//| ClassNotFoundException e) {
            throw new ConnectionPoolException(e.getMessage(), e);
        }
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection)connection).reallyClose();
        }
    }

    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close already closed connection.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!busyConnectionQueue.remove(connection)) {
                throw new SQLException("Error removing connection from the busy connections pool.");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error allocating connection to the pool.");
            }
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }
    }
}