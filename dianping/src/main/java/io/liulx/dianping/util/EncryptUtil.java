package io.liulx.dianping.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 加密工具
 *
 * @since 2019-12-25
 */
public class EncryptUtil {

  public static byte[] pbkdf2(char[] data, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    int iterCount = 10000;
    PBEKeySpec spec = new PBEKeySpec(data, salt, iterCount, 256);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    return skf.generateSecret(spec).getEncoded();
  }

  public static String pbkdf2Hash(String data, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] hash = pbkdf2(data.toCharArray(), salt);
    return Base64.getEncoder().encodeToString(hash);
  }

  public static String pbkdf2Hash(String data, String salt)
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    return pbkdf2Hash(data, decodeBase64(salt));
  }

  public static byte[] getSalt() throws NoSuchAlgorithmException {
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    byte[] bytes = new byte[8];
    random.nextBytes(bytes);
    return bytes;
  }

  public static byte[] decodeBase64(String data) {
    return Base64.getDecoder().decode(data);
  }

  public static String encodeBase64(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }
}
