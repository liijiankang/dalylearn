package org.SQLresultSetLearn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Types;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.SchemaBuilder.FieldAssembler;
import org.apache.commons.dbcp2.BasicDataSource;
import static java.sql.Types.ARRAY;
import static java.sql.Types.BIGINT;
import static java.sql.Types.BINARY;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.NCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.NVARCHAR;
import static java.sql.Types.REAL;
import static java.sql.Types.ROWID;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TIME;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;


public class DBCPServiceUtil {
	
	public static Connection getConnection() throws SQLException {
		BasicDataSource bds = new BasicDataSource();
		Properties properties = new Properties();
		try {
			properties.load(DBCPServiceUtil.class.getClassLoader().getResourceAsStream("dbcp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bds.setDriverClassName(properties.getProperty("driver"));
		bds.setUrl(properties.getProperty("url"));
		bds.setUsername(properties.getProperty("user"));
		bds.setPassword(properties.getProperty("password"));
		return bds.getConnection();
		
	}
	
	public static void getData(Connection connection,String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.print(rs.getInt(1));
			System.out.print(rs.getString(2));
			System.out.print(rs.getString(3));
			System.out.println();
		}
	}
	
	public static void getMetaData(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();
		String tableName = meta.getTableName(1);
		final FieldAssembler<Schema> builder = SchemaBuilder.record(tableName).namespace("any.data").fields();
		 for (int i = 1; i <= columnCount; i++) {
	            switch (meta.getColumnType(i)) {
	                case CHAR:
	                case LONGNVARCHAR:
	                case LONGVARCHAR:
	                case NCHAR:
	                case NVARCHAR:
	                case VARCHAR:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().stringType().endUnion().noDefault();
	                    break;
	                case BIT:
	                case BOOLEAN:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().booleanType().endUnion().noDefault();
	                    break;

	                case INTEGER:
	                    if (meta.isSigned(i)) {
	                        builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().intType().endUnion().noDefault();
	                    } else {
	                        builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().longType().endUnion().noDefault();
	                    }
	                    break;

	                case SMALLINT:
	                case TINYINT:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().intType().endUnion().noDefault();
	                    break;

	                case BIGINT:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().longType().endUnion().noDefault();
	                    break;

	                // java.sql.RowId is interface, is seems to be database
	                // implementation specific, let's convert to String
	                case ROWID:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().stringType().endUnion().noDefault();
	                    break;

	                case FLOAT:
	                case REAL:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().floatType().endUnion().noDefault();
	                    break;

	                case DOUBLE:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().doubleType().endUnion().noDefault();
	                    break;

	                // Did not find direct suitable type, need to be clarified!!!!
	                case DECIMAL:
	                case NUMERIC:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().stringType().endUnion().noDefault();
	                    break;

	                // Did not find direct suitable type, need to be clarified!!!!
	                case DATE:
	                case TIME:
	                case TIMESTAMP:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().stringType().endUnion().noDefault();
	                    break;

	                case BINARY:
	                case VARBINARY:
	                case LONGVARBINARY:
	                case ARRAY:
	                case BLOB:
	                case CLOB:
	                    builder.name(meta.getColumnName(i)).type().unionOf().nullBuilder().endNull().and().bytesType().endUnion().noDefault();
	                    break;

	                default:
	                    throw new IllegalArgumentException("createSchema: Unknown SQL type " + meta.getColumnType(i) + " cannot be converted to Avro type");
	            }
	        }
		Schema schema = builder.endRecord();
		System.out.println(getSchema(schema));
	}
	
	public static String getSchema(Schema schema) {
		if (schema == null) {
            return "";
        }
		final String PIPE = "|";
        final String description = "";
        final String primaryKey = "0";
        final String createdTracker = "0";
        final String updatedTracker = "0";
        final String newLine = "\n";
        
        StringBuffer retVal = new StringBuffer();
        
        int totalFields = schema.getFields().size();
        int counter = 1;
        
        for (Schema.Field field: schema.getFields()) {
            String name = field.name().toLowerCase();
            String dataType = field.schema().getType().name().toLowerCase();

            retVal.append(name)
                .append(PIPE)
                .append(dataType)
                .append(PIPE)
                .append(description)
                .append(PIPE)
                .append(primaryKey)
                .append(PIPE)
                .append(createdTracker)
                .append(PIPE)
                .append(updatedTracker);

            if (counter++ < totalFields) {
                retVal.append(newLine);
            }
        }
        
		return retVal.toString();
	}
	
	public static void main(String[] args) throws SQLException {
		String sql = "select * from Student";
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		getMetaData(rs);
		
	}
	
}
