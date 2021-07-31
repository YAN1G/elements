package com.xglab301.elements.db;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HbaseUtils {

    private HBaseAdmin admin = null;
    private Configuration configuration = null;

    private HbaseUtils(){
        configuration = HBaseConfiguration.create();
        System.setProperty("hadoop.home.dir", "C:\\software\\hadoop-common-2.2.0-bin-master");
        configuration.set("hbase.zookeeper.quorum","master110.hadoop,slave111.hadoop,slave112.hadoop");  //hbase 服务地址
        configuration.set("hbase.zookeeper.property.clientPort","2181"); //端口号
        // 默认configuration中 zookeeper.znode.parent = ‘/hbase’ 与实际hbase配置不一致，需重新设置
        configuration.set("zookeeper.znode.parent","/hbase-unsecure");
        configuration.set("hbase.client.keyvalue.maxsize","20971520");
        configuration.setInt("hbase.rpc.timeout",20000);
        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HbaseUtils instance = null;

    public static synchronized HbaseUtils getInstance() {
        if(instance ==null){
            instance = new HbaseUtils();
        }
        return instance;
    }

    public HTable getTable(String tbName){
        HTable table = null;
        try {
            table = new HTable(configuration,tbName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  table;
    }

    public void put(String tbName,String rowKey,String cf,String colum,String value){
        HTable table = getTable(tbName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(colum),Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(table!=null)table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void putList(String tbName, List<Put> puts){
        HTable table = getTable(tbName);
        try {
            table.put(puts);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(table!=null) table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 查询单条数据
     * @param tableName
     * @param rowkey
     * @return
     */
    public  static Result getRow(String tableName,String rowkey){
        try( HTable table = HbaseUtils.getInstance().getTable(tableName)){
            Get get = new Get(Bytes.toBytes(rowkey));
            Result result = table.get(get);
            table.close();
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getRowQualifier(String tableName, String rowKey,
                                  String family, String
                                          qualifier) throws IOException{
        String res=null;
        HTable table = getTable(tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        System.out.println();
        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        Result result = table.get(get);
        for(Cell cell : result.rawCells()){
            res=Bytes.toString(CellUtil.cloneValue(cell));

        }
        table.close();
       // System.out.println(res);
        return res;
    }

    public static void main(String[] args) throws Exception{

        HbaseUtils.getInstance().getRowQualifier("test2","13305472008","info","idNo");
    }
}
