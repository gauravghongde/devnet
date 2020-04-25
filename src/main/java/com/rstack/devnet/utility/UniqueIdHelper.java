package com.rstack.devnet.utility;

import com.rstack.devnet.shared.PostType;

import java.time.Instant;
import java.util.zip.CRC32;

public class UniqueIdHelper {
    public static String getUniquePostId(PostType postType, String username, Instant currentTimestamp) {
        String uniqueId = getUniqueId(username.concat(currentTimestamp.toString()));
        return postType.getIdPrefix().concat(uniqueId);
    }

    public static String getUniqueId(String strToHash){
        CRC32 crc = new CRC32();
        crc.update(strToHash.getBytes());
        return Long.toHexString(crc.getValue());
    }
}
