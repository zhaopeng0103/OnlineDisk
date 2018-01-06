package com.netpan.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import com.netpan.dao.basedao.HbaseDao;
import com.netpan.dao.conn.HbaseConn;
import com.netpan.util.Constants;

public class CreateTable extends HbaseDao{
	/**
     * 创建表
     * @category create 'tableName','family1','family2','family3'
     * @param tableName
     * @param family
     * @throws Exception
     */
    public static void createTable(String tableName, String[] family) throws IOException {
        Admin admin = HbaseConn.getConn().getAdmin();

        TableName tn = TableName.valueOf(tableName);
        HTableDescriptor desc = new HTableDescriptor(tn);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tn)) {
            System.out.println("createTable => table Exists!");
            admin.disableTable(tn);
            admin.deleteTable(tn);
            admin.createTable(desc);
        } else {
            admin.createTable(desc);
            System.out.println("createTable => create Success! => " + tn);
        }
    }
    
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException {
		String[] gid = {"gid"};
		String[] user_id = {"id"};
		String[] id_user = {"user"};
		String[] email_user = {"user"};
		String[] file = {"file"};
		String[] user_file = {"file"};
		String[] follow = {"name"};
		String[] followed = {"userid"};
		String[] share = {"content"};
		String[] shareed = {"shareid"};
		
		/*createTable(Constants.TABLE_GID, gid);
		createTable(Constants.TABLE_USERId, user_id);
		createTable(Constants.TABLE_IDUSER, id_user);
		createTable(Constants.TABLE_EMAILUSER, email_user);*/
		createTable(Constants.TABLE_FILE, file);
		createTable(Constants.TABLE_USERFILE, user_file);
		createTable(Constants.TABLE_FOLLOW, follow);
		createTable(Constants.TABLE_FOLLOWED, followed);
		createTable(Constants.TABLE_SHARE, share);
		createTable(Constants.TABLE_SHAREED, shareed);
	}
}
