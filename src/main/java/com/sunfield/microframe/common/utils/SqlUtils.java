package com.sunfield.microframe.common.utils;

public class SqlUtils {

    public static enum ColumnType {
        //整型
        INT,
        //字符串类型
        VARCHAR;
    }

    //静态方法上的参数化类型，在方法调用的实参中可以赋予实际类型值，但无法在这里定义的方法体中获取它的实际类型，但可以使用它作为类型的操作
    public static<T> String inSql(String columnName,ColumnType columnType,T[] params) {
        StringBuilder builder = new StringBuilder().append(columnName).append(" in(");
        switch (columnType) {
            case INT:
                for(T param : params) {
                    builder.append(param).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append(")");
                break;
            case VARCHAR:
                for(T param : params) {
                    builder.append("'").append(param).append("',");
                }
                builder.deleteCharAt(builder.length() - 1);
                builder.append(")");
                break;
            default:
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String inSql = inSql("type",ColumnType.INT,new Integer[]{1,2,3,4,5});
        System.out.println(inSql);
        inSql = inSql("name",ColumnType.VARCHAR,new Integer[]{1,2,3,4,5});
        System.out.println(inSql);
        inSql = inSql("name",ColumnType.VARCHAR,new String[]{"Jackie","Lucy","Mike","Jim"});
        System.out.println(inSql);
    }
}
