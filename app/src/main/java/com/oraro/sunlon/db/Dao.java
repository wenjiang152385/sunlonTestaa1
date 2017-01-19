package com.oraro.sunlon.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oraro.sunlon.sunlontesta.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangwen on 2016/7/24.
 */
public class Dao {
    private static Dao dao;
    private DBSqliteOpenHelper helper;

    private Dao(Context context) {
        helper = new DBSqliteOpenHelper(context);
    }

    public static synchronized Dao getInstance(Context context) {
        if (dao == null) {
            dao = new Dao(context);
        }
        return  dao;
    }


    //增加
    public void add(String name,String psw) {
        //获取数据库对象
        SQLiteDatabase db = helper.getWritableDatabase();
        //执行SQl语句
        db.execSQL("insert into person(username,pwd) values(?,?)", new Object[]{name,psw});
        db.close();
    }

    public void update(String name, String password) {
        //根据姓名修改密码
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update person set pwd=? where username=?", new Object[]{password,name});
        db.close();
    }

    public void del(String name) {
        //根据名字删除
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from person where username=?", new Object[]{name});
        db.close();
    }

    //    public String query() {
//        Log.e("jw", "helper++++++ ="+helper );
//        //根据姓名查找密码
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Cursor cursor = db.query("person", null, null, null, null, null, null);
//        if (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndex("password"));
//            return name;
//        }
//        return null;
//    }
    public List<Person> query(){

        List<Person> data = new ArrayList<Person>();
        String name = null;
        String pwd = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            name = cursor.getString(cursor.getColumnIndex("username"));
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            person.setName(name);
            person.setPassword(pwd);
            data.add(person);
        }

        return data;
    }
    String pwd;
    public String queryByName(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query("person", null, "username = ? ", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            Log.e("wjq","pwd = " + pwd);

        }
        if (null != cursor) {
            cursor.close();
        }

        return  pwd;
    }


    public List<Person> querysByName(String name) {
        List<Person> data = new ArrayList<Person>();
        String pwd = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("person", null, "username = ? ", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            name = cursor.getString(cursor.getColumnIndex("username"));
            pwd = cursor.getString(cursor.getColumnIndex("pwd"));

            person.setName(name);
            person.setPassword(pwd);
            data.add(person);
        }


        return data;
    }
}
