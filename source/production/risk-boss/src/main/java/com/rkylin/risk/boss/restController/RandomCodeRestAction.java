package com.rkylin.risk.boss.restController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 201508240185 on 2015/9/21.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/randomCode")
public class RandomCodeRestAction {

  private  static final int WIDTH = 96;
  private  static final int HEIGHT = 33;

  @RequestMapping(value = "createCode")
  public void createRandomCode(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    response.setContentType("image/jpeg");
    try (ServletOutputStream sos = response.getOutputStream();
         ByteArrayOutputStream bos = new ByteArrayOutputStream()){
      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 0);

      BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
      Graphics g = image.getGraphics();
      char[] rands = generateCheckCode();

      drawBackground(g);
      drawRands(g, rands);
      g.dispose();

      ImageIO.write(image, "JPEG", bos);
      byte[] buf = bos.toByteArray();
      response.setContentLength(buf.length);

      sos.write(buf);

      String verifyCode = new String(rands);
      session.setAttribute("randomcode", verifyCode);
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    }
  }

  private char[] generateCheckCode() {

    String chars = "0123456789qwertyuiopasdfghjklzxcvbnm";
    char[] rands = new char[4];
    for (int i = 0; i < 4; i++) {
      SecureRandom sr = new SecureRandom();
      int rand = (int) (sr.nextDouble() * 36);
      rands[i] = chars.charAt(rand);
    }
    return rands;
  }

  private void drawRands(Graphics g, char[] rands) {

    g.setColor(Color.BLACK);
    g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 20));
    g.drawString(String.valueOf(rands[0]), 3, 17);
    g.drawString(String.valueOf(rands[1]), 20, 15);
    g.drawString(String.valueOf(rands[2]), 40, 20);
    g.drawString(String.valueOf(rands[3]), 60, 16);
  }

  private void drawBackground(Graphics g) {

    g.setColor(new Color(0xDCDCDC));
    g.fillRect(0, 0, WIDTH, HEIGHT);

    for (int i = 0; i < 100; i++) {
      SecureRandom sr = new SecureRandom();
      int x = (int) (sr.nextDouble() * WIDTH);
      int y = (int) (sr.nextDouble() * HEIGHT);
      int red = (int) (sr.nextDouble() * 255);
      int green = (int) (sr.nextDouble() * 255);
      int blue = (int) (sr.nextDouble() * 255);
      g.setColor(new Color(red, green, blue));
      g.drawOval(x, y, 1, 0);
    }
  }
}
