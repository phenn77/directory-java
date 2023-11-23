package com.training.directory.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.training.directory.exception.BusinessException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class ApplicationUtil {
    public ApplicationUtil() {
    }

    public static String hashCredential(String credential) {
        return BCrypt.withDefaults().hashToString(12, credential.toCharArray());
    }

    public static void validateCredential(String initial, String current) {
        BCrypt.Result result = BCrypt.verifyer().verify(current.toCharArray(), initial);

        if (BooleanUtils.isFalse(result.verified)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Password not match.");
        }
    }

    public static String convertTime(String format, LocalDateTime date) {
        var spf = new SimpleDateFormat(format);

        return spf.format(date);
    }
}
