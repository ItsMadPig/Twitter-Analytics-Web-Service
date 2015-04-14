import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


public class LoadData2Hbase {

	//zxflux_2014-03-21_447042983834451968,1511750340,2014-03-21+16:12:07;447047035553054720,82177855,2014-03-21+16:28:13;447054417498763264,1219727946,2014-03-21+16:57:33;447061120021721088,1511747294,2014-03-21+17:24:11;447062931940073472,297882059,2014-03-21+17:31:23;
	static final byte[] tableName = Bytes.toBytes("hbase-q4-test");
	static final byte[] family = Bytes.toBytes("f");
	static	Configuration config = HBaseConfiguration.create();
	static HConnection connection = null;
	static {
		try {
			config.set("hbase.zookeeper.quorum", "ip-172-31-58-11.ec2.internal");
			connection = HConnectionManager.createConnection(config); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		/*
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("_");
				String tag = parts[0];
				String date = parts[1];
				String text = parts[2];
				put2Table(tag, date2Index(date),text);
			}
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}*/
		String line = "zxflux_2014-03-21_447042983834451968,1511750340,2014-03-21+16:12:07;"
				+ "447047035553054720,82177855,2014-03-21+16:28:13;"
				+ "447054417498763264,1219727946,2014-03-21+16:57:33;"
				+ "447061120021721088,1511747294,2014-03-21+17:24:11;"
				+ "447062931940073472,297882059,2014-03-21+17:31:23;";
		String[] parts = line.split("_");
		String tag = parts[0];
		String date = parts[1];
		String text = parts[2];
		put2Table(tag, date2Index(date),text);
		
	}
	private static void put2Table(String tag, int index, String text) {
		byte[] qualifier = Bytes.toBytes("c"+index);
		byte[] value = Bytes.toBytes(text); 
		HTable table = null;
		try {
			table = new HTable(tableName,  connection);
			Put put = new Put(Bytes.toBytes(tag));
		    put.add(family, qualifier, value);
		    table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static int date2Index(String dateString) {
		String beginDateString = "2014-03-21";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate = format.parse(dateString);
			Date beginDate = format.parse(beginDateString);
			long diff = Math.abs(currentDate.getTime() - beginDate.getTime());
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diffDays > 97) {
				System.out.println("Date is larger than 97 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
			return (int)diffDays;
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
