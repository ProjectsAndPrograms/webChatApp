package mypackage;

import java.io.File;

public class Const {

	public static String projectPath = new File("").getAbsolutePath();
	public static String app_name = "Chat Application";

	
	// unique id
	 public static final long EPOCH = 1609459200000L; // Adjust this to your needs
	 public static final long SEQUENCE_BITS = 12;
	 public static final long MACHINE_ID_BITS = 10;
	 public static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;
	 public static final long MACHINE_ID = 42; // Unique to each machine

	 public static long lastTimestamp = -1L;
	 public static long sequence = 0L;
	 
	  public static long tilNextMillis(long lastTimestamp) {
	        long timestamp = System.currentTimeMillis();
	        while (timestamp <= lastTimestamp) {
	            timestamp = System.currentTimeMillis();
	        }
	        return timestamp;
	    }
	  
	  public static synchronized long generateUniqueID() {
	        long timestamp = System.currentTimeMillis() - EPOCH;

	        if (timestamp == lastTimestamp) {
	            sequence = (sequence + 1) & MAX_SEQUENCE;
	            if (sequence == 0) {
	                timestamp = tilNextMillis(lastTimestamp);
	            }
	        } else {
	            sequence = 0;
	        }

	        lastTimestamp = timestamp;

	        return (timestamp << (SEQUENCE_BITS + MACHINE_ID_BITS)) |
	               (MACHINE_ID << SEQUENCE_BITS) |
	               sequence;
	    }
	 
}
