package me.logon.projecttalon;

import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Utilities {

    private static final int POSITION_VARIANCE = 5;

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void leftClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getRandomNumberInRange(45, 200));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void leftClickWithDelay(Robot robot) {
        robot.delay(getRandomNumberInRange(350, 1250));
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getRandomNumberInRange(45, 200));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

//	public static void middleClick(Robot robot) {
//		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
//	}

    public static void doubleLeftClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getRandomNumberInRange(45, 200));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getRandomNumberInRange(45, 150));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void rightClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(getRandomNumberInRange(20, 100));
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }

    public static void doubleRightClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(getRandomNumberInRange(20, 100));
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
        robot.delay(getRandomNumberInRange(20, 100));
        robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }

    public static void moveCursorToPositionImmediate(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
    }

    public static void moveCursorToPosition(Robot robot, int x, int y) {
        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
        int counter = 0;
        while(Math.abs(mouseX - x) > 3 || Math.abs(mouseY - y) > 3 && counter < 20000) {
            if(mouseX > x && mouseY > y) {
                mouseX = mouseX - 1;
                mouseY = mouseY - 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX < x && mouseY < y) {
                mouseX = mouseX + 1;
                mouseY = mouseY + 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX < x && mouseY > y) {
                mouseX = mouseX + 1;
                mouseY = mouseY - 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX > x && mouseY < y) {
                mouseX = mouseX - 1;
                mouseY = mouseY + 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX == x && mouseY < y) {
                mouseY = mouseY + 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX == x && mouseY > y) {
                mouseY = mouseY - 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX < x && mouseY == y) {
                mouseX = mouseX + 1;
                robot.mouseMove(mouseX, mouseY);
            } else if(mouseX > x && mouseY == y) {
                mouseX = mouseX - 1;
                robot.mouseMove(mouseX, mouseY);
            }
            robot.delay(3);
            counter++;
            //System.out.println(counter + " : " + mouseX + " : " + mouseY);
        }
    }

    public static void moveMouseToRandomPositionWithDelay(Robot robot) {
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        int randomX = getRandomNumberInRange((int) capture.getMinX() + 50, (int) capture.getMaxX() - 50);
        int randomY = getRandomNumberInRange((int) capture.getMinY() + 50, (int) capture.getMaxY() - 50);
        robot.delay(getRandomNumberInRange(300, 2000));
        humanMoveTowards(robot, randomX, randomY);
    }


    public static int getPixelColor(Robot robot) {
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
        System.out.println("X: " + mouseX);
        System.out.println("Y: " + mouseY);
        int ARGB = image.getRGB(mouseX, mouseY);
        int alpha = (ARGB >> 24) & 255;
        int red = (ARGB >> 16) & 255;
        int green = (ARGB >> 8) & 255;
        int blue = ARGB & 255;
        System.out.println("A: " + alpha);
        System.out.println("R: " + red);
        System.out.println("G: " + green);
        System.out.println("B: " + blue);
        return ARGB;
    }

    public static void pictureToFrame(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jsp = new JScrollPane(new JLabel(new ImageIcon(image)), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(jsp);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public static void pictureToFrame(Image image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jsp = new JScrollPane(new JLabel(new ImageIcon(image)), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(jsp);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public static void pictureReplaceColor(BufferedImage image, int ARGB) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int clr = image.getRGB(x, y);
                //int red = (clr & 0x00ff0000) >> 16;
                //int green = (clr & 0x0000ff00) >> 8;
                //int blue = clr & 0x000000ff;
                if (clr == ARGB) {
                    image.setRGB(x, y, Color.PINK.getRGB());
                }
            }
        }
        pictureToFrame(image);
    }

    public static int[] pictureReplaceColorRange(BufferedImage image, Color rgb, int pixelDiff) {
        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int clr = image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;

                //int clr2 = rgb.getRGB();
                int red2 = rgb.getRed();
                int green2 = rgb.getGreen();
                int blue2 = rgb.getBlue();
                if (Math.abs(red - red2) <= pixelDiff && Math.abs(green - green2) <= pixelDiff && Math.abs(blue - blue2) <= pixelDiff) {
                    image.setRGB(x, y, Color.GREEN.getRGB());
                    xValues.add(x);
                    yValues.add(y);
                    //System.out.println("test");
                }
            }
        }
        int xSum = 0;
        for(int x : xValues) {
            //System.out.println(x);
            xSum = xSum + x;
        }
        int ySum = 0;
        for(int y : yValues) {
            ySum = ySum + y;
        }

        int[] result = new int[2];
        result[0] = xSum / xValues.size();
        result[1] = ySum / yValues.size();
        pictureToFrame(image);
        return result;
    }

    public static void typeString(Robot robot, String str, boolean pressEnter) {
        for(int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 'a':
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_A);
                    break;
                case 'b':
                    robot.keyPress(KeyEvent.VK_B);
                    robot.keyRelease(KeyEvent.VK_B);
                    break;
                case 'c':
                    robot.keyPress(KeyEvent.VK_C);
                    robot.keyRelease(KeyEvent.VK_C);
                    break;
                case 'd':
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_D);
                    break;
                case 'e':
                    robot.keyPress(KeyEvent.VK_E);
                    robot.keyRelease(KeyEvent.VK_E);
                    break;
                case 'f':
                    robot.keyPress(KeyEvent.VK_F);
                    robot.keyRelease(KeyEvent.VK_F);
                    break;
                case 'g':
                    robot.keyPress(KeyEvent.VK_G);
                    robot.keyRelease(KeyEvent.VK_G);
                    break;
                case 'h':
                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyRelease(KeyEvent.VK_H);
                    break;
                case 'i':
                    robot.keyPress(KeyEvent.VK_I);
                    robot.keyRelease(KeyEvent.VK_I);
                    break;
                case 'j':
                    robot.keyPress(KeyEvent.VK_J);
                    robot.keyRelease(KeyEvent.VK_J);
                    break;
                case 'k':
                    robot.keyPress(KeyEvent.VK_K);
                    robot.keyRelease(KeyEvent.VK_K);
                    break;
                case 'l':
                    robot.keyPress(KeyEvent.VK_L);
                    robot.keyRelease(KeyEvent.VK_L);
                    break;
                case 'm':
                    robot.keyPress(KeyEvent.VK_M);
                    robot.keyRelease(KeyEvent.VK_M);
                    break;
                case 'n':
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_N);
                    break;
                case 'o':
                    robot.keyPress(KeyEvent.VK_O);
                    robot.keyRelease(KeyEvent.VK_O);
                    break;
                case 'p':
                    robot.keyPress(KeyEvent.VK_P);
                    robot.keyRelease(KeyEvent.VK_P);
                    break;
                case 'q':
                    robot.keyPress(KeyEvent.VK_Q);
                    robot.keyRelease(KeyEvent.VK_Q);
                    break;
                case 'r':
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_R);
                    break;
                case 's':
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_S);
                    break;
                case 't':
                    robot.keyPress(KeyEvent.VK_T);
                    robot.keyRelease(KeyEvent.VK_T);
                    break;
                case 'u':
                    robot.keyPress(KeyEvent.VK_U);
                    robot.keyRelease(KeyEvent.VK_U);
                    break;
                case 'v':
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                    break;
                case 'w':
                    robot.keyPress(KeyEvent.VK_W);
                    robot.keyRelease(KeyEvent.VK_W);
                    break;
                case 'x':
                    robot.keyPress(KeyEvent.VK_X);
                    robot.keyRelease(KeyEvent.VK_X);
                    break;
                case 'y':
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.keyRelease(KeyEvent.VK_Y);
                    break;
                case 'z':
                    robot.keyPress(KeyEvent.VK_Z);
                    robot.keyRelease(KeyEvent.VK_Z);
                    break;
                case 'A':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_A);
                    break;
                case 'B':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_B);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_B);
                    break;
                case 'C':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_C);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_C);
                    break;
                case 'D':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_D);
                    break;
                case 'E':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_E);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_E);
                    break;
                case 'F':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_F);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_F);
                    break;
                case 'G':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_G);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_G);
                    break;
                case 'H':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_H);
                    break;
                case 'I':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_I);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_I);
                    break;
                case 'J':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_J);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_J);
                    break;
                case 'K':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_K);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_K);
                    break;
                case 'L':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_L);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_L);
                    break;
                case 'M':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_M);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_M);
                    break;
                case 'N':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_N);
                    break;
                case 'O':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_O);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_O);
                    break;
                case 'P':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_P);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_P);
                    break;
                case 'Q':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_Q);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_Q);
                    break;
                case 'R':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_R);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_R);
                    break;
                case 'S':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_S);
                    break;
                case 'T':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_T);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_T);
                    break;
                case 'U':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_U);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_U);
                    break;
                case 'V':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_V);
                    break;
                case 'W':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_W);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_W);
                    break;
                case 'X':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_X);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_X);
                    break;
                case 'Y':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_Y);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_Y);
                    break;
                case 'Z':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_Z);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_Z);
                    break;
                case '`':
                    robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                    robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                    break;
                case '0':
                    robot.keyPress(KeyEvent.VK_0);
                    robot.keyRelease(KeyEvent.VK_0);
                    break;
                case '1':
                    robot.keyPress(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_1);
                    break;
                case '2':
                    robot.keyPress(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_2);
                    break;
                case '3':
                    robot.keyPress(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_3);
                    break;
                case '4':
                    robot.keyPress(KeyEvent.VK_4);
                    robot.keyRelease(KeyEvent.VK_4);
                    break;
                case '5':
                    robot.keyPress(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_5);
                    break;
                case '6':
                    robot.keyPress(KeyEvent.VK_6);
                    robot.keyRelease(KeyEvent.VK_6);
                    break;
                case '7':
                    robot.keyPress(KeyEvent.VK_7);
                    robot.keyRelease(KeyEvent.VK_7);
                    break;
                case '8':
                    robot.keyPress(KeyEvent.VK_8);
                    robot.keyRelease(KeyEvent.VK_8);
                    break;
                case '9':
                    robot.keyPress(KeyEvent.VK_9);
                    robot.keyRelease(KeyEvent.VK_9);
                    break;
                case '-':
                    robot.keyPress(KeyEvent.VK_MINUS);
                    robot.keyRelease(KeyEvent.VK_MINUS);
                    break;
                case '=':
                    robot.keyPress(KeyEvent.VK_EQUALS);
                    robot.keyRelease(KeyEvent.VK_EQUALS);
                    break;
                case '~':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                    break;
                case '!':
                    robot.keyPress(KeyEvent.VK_EXCLAMATION_MARK);
                    robot.keyRelease(KeyEvent.VK_EXCLAMATION_MARK);
                    break;
                case '@':
                    robot.keyPress(KeyEvent.VK_AT);
                    robot.keyRelease(KeyEvent.VK_AT);
                    break;
                case '#':
                    robot.keyPress(KeyEvent.VK_NUMBER_SIGN);
                    robot.keyRelease(KeyEvent.VK_NUMBER_SIGN);
                    break;
                case '$':
                    robot.keyPress(KeyEvent.VK_DOLLAR);
                    robot.keyRelease(KeyEvent.VK_DOLLAR);
                    break;
                case '%':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_5);
                    break;
                case '^':
                    robot.keyPress(KeyEvent.VK_CIRCUMFLEX);
                    robot.keyRelease(KeyEvent.VK_CIRCUMFLEX);
                    break;
                case '&':
                    robot.keyPress(KeyEvent.VK_AMPERSAND);
                    robot.keyRelease(KeyEvent.VK_AMPERSAND);
                    break;
                case '*':
                    robot.keyPress(KeyEvent.VK_ASTERISK);
                    robot.keyRelease(KeyEvent.VK_ASTERISK);
                    break;
                case '(':
                    robot.keyPress(KeyEvent.VK_LEFT_PARENTHESIS);
                    robot.keyRelease(KeyEvent.VK_LEFT_PARENTHESIS);
                    break;
                case ')':
                    robot.keyPress(KeyEvent.VK_RIGHT_PARENTHESIS);
                    robot.keyRelease(KeyEvent.VK_RIGHT_PARENTHESIS);
                    break;
                case '_':
                    robot.keyPress(KeyEvent.VK_UNDERSCORE);
                    robot.keyRelease(KeyEvent.VK_UNDERSCORE);
                    break;
                case '+':
                    robot.keyPress(KeyEvent.VK_PLUS);
                    robot.keyRelease(KeyEvent.VK_PLUS);
                    break;
                case '\t':
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    break;
                case '\n':
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    break;
                case '[':
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                    break;
                case ']':
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                    break;
                case '\\':
                    robot.keyPress(KeyEvent.VK_BACK_SLASH);
                    robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                    break;
                case '{':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                    break;
                case '}':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                    break;
                case '|':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_BACK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                    break;
                case ';':
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    break;
                case ':':
                    robot.keyPress(KeyEvent.VK_COLON);
                    robot.keyRelease(KeyEvent.VK_COLON);
                    break;
                case '\'':
                    robot.keyPress(KeyEvent.VK_QUOTE);
                    robot.keyRelease(KeyEvent.VK_QUOTE);
                    break;
                case '"':
                    robot.keyPress(KeyEvent.VK_QUOTEDBL);
                    robot.keyRelease(KeyEvent.VK_QUOTEDBL);
                    break;
                case ',':
                    robot.keyPress(KeyEvent.VK_COMMA);
                    robot.keyRelease(KeyEvent.VK_COMMA);
                    break;
                case '<':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_COMMA);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_COMMA);
                    break;
                case '.':
                    robot.keyPress(KeyEvent.VK_PERIOD);
                    robot.keyRelease(KeyEvent.VK_PERIOD);
                    break;
                case '>':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_PERIOD);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_PERIOD);
                    break;
                case '/':
                    robot.keyPress(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SLASH);
                    break;
                case '?':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_SLASH);
                    break;
                case ' ':
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    break;
                default:
                    throw new IllegalArgumentException("Cannot type character: " + str.charAt(i));
            }
            robot.delay(getRandomNumberInRange(40, 150));
        }
        if(pressEnter) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    /**
     *  Humanish movements:
     *  - Final resting point variance
     *  - Non-linear movements
     */
    public static void humanMoveTowards(Robot robot, int x, int y){
        Point start = MouseInfo.getPointerInfo().getLocation();
        Point newDest = generatePointVariant(new Point(x,y));

        java.util.List<Point> path = BezierPathGenerator.generateCubicBezierCurve(start, newDest);

        for (Point p : path){
            robot.mouseMove(p.x, p.y);
            robot.delay(getRandomNumberInRange(3, 10));
        }
    }

    private static Point generatePointVariant(Point p){
        int newX = p.x + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        int newY = p.y + ThreadLocalRandom.current().nextInt(-POSITION_VARIANCE, POSITION_VARIANCE);
        return new Point(newX, newY);
    }

}