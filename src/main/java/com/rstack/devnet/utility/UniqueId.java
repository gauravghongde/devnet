package com.rstack.devnet.utility;

import java.util.zip.CRC32;

public class UniqueId {
    public static String getUniqueId(String strToHash){
        CRC32 crc = new CRC32();
        crc.update(strToHash.getBytes());
        return Long.toHexString(crc.getValue());
    }
}
