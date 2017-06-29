package com.rkylin.risk.credit.founder.jackson;

import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by tomalloc on 16-7-6.
 */
public class DesUtils {
  // 算法名称
  public static final String KEY_ALGORITHM = "DES";
  // 算法名称/加密模式/填充方式
  public static final String CIPHER_ALGORITHM_ECB = "DES/ECB/PKCS7Padding";
  //    public static final String CIPHER_ALGORITHM_CBC = "DES/CBC/ZerosPadding";

  static {
    Security.addProvider(new BouncyCastleProvider());
  }
  /**
   * 生成密钥
   *
   * @throws Exception
   */
  private static byte[] generateKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
    keyGenerator.init(56); //des 必须是56, 此初始方法不必须调用
    SecretKey secretKey = keyGenerator.generateKey();
    return secretKey.getEncoded();
  }

  /**
   * 还原密钥
   *
   * @throws Exception
   */
  private static Key toKey(byte[] key) throws Exception {
    DESKeySpec des = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
    SecretKey secretKey = keyFactory.generateSecret(des);
    return secretKey;
  }

  /**
   * 加密
   *
   * @param data 原文
   * @return 密文
   * @throws Exception
   */
  public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    Key k = toKey(key);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
    cipher.init(Cipher.ENCRYPT_MODE, k);
    byte[] doFinal = cipher.doFinal(data);
    return cipher.doFinal(data);
  }

  /**
   * 解密
   *
   * @param data 密文
   * @return 明文、原文
   * @throws Exception
   */
  public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
    Key k = toKey(key);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
    cipher.init(Cipher.DECRYPT_MODE, k);
    return cipher.doFinal(data);
  }
}
