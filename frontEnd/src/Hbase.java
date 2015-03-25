import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class Hbase {
     static Configuration conf = null;
        static {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "ec2-52-1-183-15.compute-1.amazonaws.com");
            conf.set("hbase.master", "ec2-52-1-183-15.compute-1.amazonaws.com"); 
            conf.set("hbase.zookeeper.property.clientPort", "2181");
           
        }

    public static void main(String[] args) throws IOException {
        String tableName="twitter";
        String rowKey="2257356221_2014-05-29 21:44:18";
        String column="response";
        HTable table = new HTable(conf, tableName);
        byte[] row = Bytes.toBytes(rowKey);
        byte[] col1 = Bytes.toBytes(column);
        System.out.println("great");
        //Get get = new Get(row);
        //Result result = table.get(get);
        
        
        


    }
}