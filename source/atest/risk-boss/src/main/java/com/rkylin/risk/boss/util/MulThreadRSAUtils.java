package com.rkylin.risk.boss.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @version 1.0
 * @date 2012-4-26
 */
public class MulThreadRSAUtils {

  /**
   * 加密算法RSA
   */
  public static final String KEY_ALGORITHM = "RSA";

  /**
   * RSA最大解密密文大小
   */
  private static final int MAX_DECRYPT_BLOCK = 128;

  private static ThreadPoolTaskExecutor threadPoolTaskExecutor;

  static {
    threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(10);
    threadPoolTaskExecutor.setMaxPoolSize(20);
    threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    threadPoolTaskExecutor.initialize();
  }

  public static Key generatePrivatekey(String privateKey)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] keyBytes = DatatypeConverter.parseBase64Binary(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    return keyFactory.generatePrivate(pkcs8KeySpec);
  }

  public static byte[] multiThreadDecryp(byte[] encryptedData, final int decryptionThreadSize,
      final Key privateK) throws IOException, ExecutionException, InterruptedException {
    final List<Entry> list = new LinkedList<Entry>();
    int inputLen = encryptedData.length;
    int startIndex = 0;
    int offSet = 0;
    int decryptTimes = -1;

    decryptTimes = inputLen / MAX_DECRYPT_BLOCK;
    offSet = inputLen % MAX_DECRYPT_BLOCK;
    if (offSet > 0) {
      decryptTimes++;
    } else {
      if (decryptTimes > 0) {
        offSet = MAX_DECRYPT_BLOCK;
      }
    }
    int length = MAX_DECRYPT_BLOCK;
    for (int j = 0; j < decryptTimes; j++) {
      Entry entry = new Entry();
      entry.setIndex(j);
      if (j == decryptTimes - 1) {
        length = offSet;
      }
      byte[] tem = new byte[length];
      System.arraycopy(encryptedData, startIndex, tem, 0, length);
      entry.setData(tem);
      list.add(entry);
      startIndex = length * (j + 1);
    }
    // 对数据分段解密
    final Map<Integer, List<Entry>> calcTask = calcTask(list, decryptionThreadSize);
    int taskSize = calcTask.size();
    List<Future<List<Entry>>> enList = new ArrayList<Future<List<Entry>>>();
    ExecutorService service = threadPoolTaskExecutor.getThreadPoolExecutor();
    for (int j = 0; j < taskSize; j++) {
      final int index = j;
      Future<List<Entry>> submit = service.submit(new Callable<List<Entry>>() {
        @Override
        public List<Entry> call() throws Exception {
          Cipher cipher = Cipher.getInstance(privateK.getAlgorithm());
          cipher.init(Cipher.DECRYPT_MODE, privateK);
          List<Entry> list2 = calcTask.get(index);
          for (Entry entry : list2) {
            byte[] enDatas = cipher.doFinal(entry.getData());
            entry.setData(enDatas);
          }
          return list2;
        }
      });
      enList.add(submit);
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    for (Future<List<Entry>> fle : enList) {
      List<Entry> list2 = fle.get();
      for (Entry e : list2) {
        bos.write(e.getData());
      }
    }
    return bos.toByteArray();
  }


  /** *//**
   * <p>
   * 公钥解密
   * </p>
   *
   * @param encryptedData 已加密数据
   * @param publicKey 公钥(BASE64编码)
   * @return
   * @throws Exception
   */
  public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
      throws Exception {
    byte[] keyBytes =  DatatypeConverter.parseBase64Binary(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, publicK);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (inputLen - offSet > 0) {
      if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
      } else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * MAX_DECRYPT_BLOCK;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }


  static class Entry implements Comparable<Entry> {
    private int index;
    private byte[] data;

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public byte[] getData() {
      return data;
    }

    public void setData(byte[] data) {
      this.data = data;
    }

    @Override
    public int compareTo(Entry o) {
      return this.index - o.index;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Entry entry = (Entry) o;

      if (index != entry.index) return false;
      return Arrays.equals(data, entry.data);
    }

    @Override
    public int hashCode() {
      int result = index;
      result = 31 * result + Arrays.hashCode(data);
      return result;
    }
  }

  public static <E> Map<Integer, List<E>> calcTask(List<E> tasks, int poolsize) {
    Map<Integer, List<E>> maps = new HashMap<Integer, List<E>>();
    int taskNum = tasks.size();
    int avageTasks = taskNum / poolsize + 1;
    for (int i = 0; i < poolsize; i++) {
      List<E> list = new LinkedList<E>();
      int start = avageTasks * i;
      int end = Math.min(avageTasks * (i + 1), taskNum);
      for (int j = start; j < end; j++) {
        list.add(tasks.get(j));
      }
      if (!list.isEmpty()) {
        maps.put(i, list);
      }
    }
    return maps;
  }
}
