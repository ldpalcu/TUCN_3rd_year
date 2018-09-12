package data_access.dao;

import data_access.connection.ConnectionDatabase;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Daniela Palcu, created on 18.03.2018
 * This class contains methods which represents the CRUD operations.
 * It is a generic class.
 * This class is inspired from my Programming Techniques project which I have done last year.
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    public AbstractDAO() {

        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    public void print() {

        T instance = null;
        try {
            for (Field field : type.getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(instance);
                System.out.println(value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                    //System.out.println(value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createSelectQuery(ArrayList<String> fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        for (int i = 0; i < fields.size(); i++){
            sb.append(fields.get(i) + " =?");
            if (i <= fields.size() - 2){
                sb.append(" AND ");
            }
        }
        System.out.println(sb);
        return sb.toString();
    }


    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append(type.getSimpleName());
        Field[] fields = type.getDeclaredFields();
        sb.append(" (");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append(fields[i].getName() + ",");
        }
        sb.append(fields[fields.length - 1].getName() + ")");
        sb.append(" VALUES(");
        for (int i = 0; i < fields.length - 1; i++) {
            sb.append("?,");
        }
        sb.append("?");
        sb.append(")");
        //System.out.println(sb);

        return sb.toString();

    }

    private String createUpdateQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length - 1; i++) {
            sb.append(fields[i].getName() + "= ?, ");
        }
        sb.append(fields[fields.length - 1].getName() + "= ? ");
        sb.append(" WHERE ");
        sb.append(field + "= ? ");
        //System.out.println(sb);

        return sb.toString();


    }

    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(field + "= ?");
        //System.out.println(sb);
        return sb.toString();
    }

    public T findById(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<T> objects;

        Field[] fields = type.getDeclaredFields();
        ArrayList<String> usedFields = new ArrayList<>();
        usedFields.add(fields[0].getName());
        System.out.println(fields[0].getName());
        String query = createSelectQuery(usedFields);

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            objects = createObjects(resultSet);
            if (objects.size() == 0) return null;
            return objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionDatabase.close(resultSet);
            ConnectionDatabase.close(statement);
            ConnectionDatabase.close(connection);
        }
        return null;

    }

    public void insertQuery(T instance) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertStatement = createInsertQuery();
        try {
            connection = ConnectionDatabase.getConnection();
            preparedStatement = connection.prepareStatement(insertStatement);


            //for (T instance : list) {
                int index = 0;

                for (Field field : type.getDeclaredFields()) {

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    Object value = method.invoke(instance);
                    preparedStatement.setObject(++index, value);
                }

            //}
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConnectionDatabase.close(preparedStatement);
            ConnectionDatabase.close(connection);

        }
    }

    public void updateQuery(T instance, long id) {
        Connection dbConnection = ConnectionDatabase.getConnection();
        Field[] fields = type.getDeclaredFields();

        PreparedStatement updateStatement = null;
        String updateStatementString = createUpdateQuery(fields[0].getName());
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);

            //for (T instance : list) {

                for (int i = 1; i < fields.length; i++) {

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fields[i].getName(), type);
                    Method method = propertyDescriptor.getReadMethod();
                    Object value = method.invoke(instance);
                    updateStatement.setObject(i, value);
                    // System.out.println(value);
                }

            //}

            updateStatement.setLong(fields.length, id);

            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConnectionDatabase.close(updateStatement);
            ConnectionDatabase.close(dbConnection);
        }

    }

    public void deleteQuery(long id) {
        Connection dbConnection = ConnectionDatabase.getConnection();
        Field[] fields = type.getDeclaredFields();


        PreparedStatement deleteStatement = null;
        String deleteStatementString = createDeleteQuery(fields[0].getName());
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setLong(1, id);

            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDatabase.close(deleteStatement);
            ConnectionDatabase.close(dbConnection);
        }
    }

    public T findByUsernameAndPassword(String user, String pass) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<T> objects = null;

        Field[] fields = type.getDeclaredFields();
        ArrayList<String> usedFields = new ArrayList<>();
        for (Field field : fields){
            if (field.getName() == "username" ||
                    field.getName() == "password"){
                usedFields.add(field.getName());
            }

        }
        String query = createSelectQuery(usedFields);

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, pass);
            resultSet = statement.executeQuery();

            objects = createObjects(resultSet);
            if (objects.size() == 0) return null;
            return  objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByUsernameAndPassword " + e.getMessage());
        } finally {
            ConnectionDatabase.close(resultSet);
            ConnectionDatabase.close(statement);
            ConnectionDatabase.close(connection);
        }
        return null;
    }


    public ArrayList<T> findByUserName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<T> objects = null;

        Field[] fields = type.getDeclaredFields();
        ArrayList<String> usedFields = new ArrayList<>();
        usedFields.add("username");

        String query = createSelectQuery(usedFields);

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            //TODO handle null pointer exception
            objects = createObjects(resultSet);
            if (objects.size() == 0) return null;
            return  objects;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionDatabase.close(resultSet);
            ConnectionDatabase.close(statement);
            ConnectionDatabase.close(connection);
        }
        return null;
    }

    public T findByIdClientAndAccount(long idAccount, long idClient){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<T> objects = null;

        ArrayList<String> usedFields = new ArrayList<>();
        usedFields.add("idAccount");
        usedFields.add("idClient");

        String query = createSelectQuery(usedFields);

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(2, idClient);
            statement.setLong(1, idAccount);
            resultSet = statement.executeQuery();

            objects = createObjects(resultSet);
            if (objects.size() == 0) return null;
            return  objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionDatabase.close(resultSet);
            ConnectionDatabase.close(statement);
            ConnectionDatabase.close(connection);
        }
        return null;
    }

    public T findByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<T> objects = null;

        ArrayList<String> usedFields = new ArrayList<>();
        usedFields.add("name");

        String query = createSelectQuery(usedFields);

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            objects = createObjects(resultSet);
            if (objects.size() == 0) return null;
            return  objects.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        }

        return null;
    }

}
