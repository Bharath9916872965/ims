package com.vts.ims.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DLocalConvertion {

	public static LocalDateTime converLocalTime(LocalDateTime localDateTime) {
        ZonedDateTime scheduleDateUtc = localDateTime.atZone(ZoneId.of("UTC"));

        ZonedDateTime scheduleDateIst = scheduleDateUtc.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

       return scheduleDateIst.toLocalDateTime();
	}
}
