package io.liulx.dianping.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;

class EncryptUtilTest {

  @Test
  void pbkdf2Hash() throws InvalidKeySpecException, NoSuchAlgorithmException {
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[64];
    random.nextBytes(salt);
    String result1 = EncryptUtil.pbkdf2Hash("hello world", salt);
    String result2 = EncryptUtil.pbkdf2Hash("hello world", salt);
    assertEquals(result1, result2);
  }
}