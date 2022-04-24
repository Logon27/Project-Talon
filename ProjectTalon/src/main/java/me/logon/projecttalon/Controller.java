package me.logon.projecttalon;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Controller {

    public static boolean running = false;

    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private Thread botThread;

    @FXML
    private ListView<String> rectList;
    @FXML
    private ProgressBar progBar;
    @FXML
    private CheckBox repeatCheckBox;
    @FXML
    private CheckBox clickCheckBox;
    @FXML
    private Spinner<Integer> secondDelay;

    @FXML
    public void initialize() {
        rectList.getItems().add("");
    }

    public void addRegionButtonClicked() {
        System.out.println("Add Region Button Clicked");

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.delay(3000);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));

        ScreenCaptureRectangle capture = new ScreenCaptureRectangle(screen);
        if(capture.getRect() != null) {
            int minX = (int) capture.getRect().getMinX();
            int minY = (int) capture.getRect().getMinY();
            int maxX = (int) capture.getRect().getMaxX();
            int maxY = (int) capture.getRect().getMaxY();
            if(rectList.getItems().get(0) == "") {
                rectList.getItems().clear();
            }
            rectList.getItems().add("[(" + minX + ", " + minY + "), (" + maxX + ", " + maxY +")]");
            rectangles.add(capture.getRect());
        }
    }

    public void clearRegionsButtonClicked() {
        System.out.println("Clear Regions Button Clicked");
        rectList.getItems().clear();
        rectList.getItems().add("");
        rectangles.clear();
    }

/*    public void startButtonClicked() {
        System.out.println("Start Button Clicked");
        progBar.setProgress(0);
        botThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                if(rectangles != null && !rectangles.isEmpty()) {
                    running = true;
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                    robot.delay(3000);
                    do{
                        for (int i = 0; i < rectangles.size(); i++) {
                            if (running) {
                                int selectedX = Utilities.getRandomNumberInRange((int) rectangles.get(i).getMinX(), (int) rectangles.get(i).getMaxX());
                                int selectedY = Utilities.getRandomNumberInRange((int) rectangles.get(i).getMinY(), (int) rectangles.get(i).getMaxY());
                                Utilities.humanMoveTowards(robot, selectedX, selectedY);
                                if(clickCheckBox.isSelected()) {
                                    Utilities.leftClickWithDelay(robot);
                                }
                                double progress = (double) (i + 1) / (double) rectangles.size();
                                progBar.setProgress(progress);
                                Utilities.moveMouseToRandomPositionWithDelay(robot);
                                if (!(i == rectangles.size() - 1) || repeatCheckBox.isSelected()) {
                                    robot.delay(Utilities.getRandomNumberInRange((1000 * secondDelay.getValue()) - 7, (1000 * secondDelay.getValue()) + 7));
                                }
                            } else {
                                return;
                            }
                        }
                    } while(repeatCheckBox.isSelected());
                    System.out.println("Finished Running");
                    progBar.setProgress(0);
                    return;
                } else {
                    System.out.println("No rectangles set.");
                    return;
                }
            }
        });
        botThread.start();
    }*/

    public void startButtonClicked() {
        System.out.println("Start Button Clicked");
        progBar.setProgress(0);
        botThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                if(rectList.getItems() != null && !rectList.getItems().isEmpty()) {
                    running = true;

                    Robot robot = null;

                    try {
                        robot = new Robot();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                    robot.delay(3000);
                    do{
                        int rectangleCounter = 0;
                        for (int i = 0; i < rectList.getItems().size(); i++) {
                            if (running) {
                                if(rectList.getItems().get(i).charAt(0) == '[') {
                                    int selectedX = Utilities.getRandomNumberInRange((int) rectangles.get(i).getMinX(), (int) rectangles.get(i).getMaxX());
                                    int selectedY = Utilities.getRandomNumberInRange((int) rectangles.get(i).getMinY(), (int) rectangles.get(i).getMaxY());
                                    Utilities.humanMoveTowards(robot, selectedX, selectedY);
                                    if(clickCheckBox.isSelected()) {
                                        Utilities.leftClickWithDelay(robot);
                                    }
                                    Utilities.moveMouseToRandomPositionWithDelay(robot);
                                } else {
                                    if(rectList.getItems().get(i).contains("/Enter/")) {
                                        Utilities.typeString(robot, rectList.getItems().get(i).substring(0, rectList.getItems().get(i).length()-8), true);
                                    } else {
                                        Utilities.typeString(robot, rectList.getItems().get(i).substring(0, rectList.getItems().get(i).length()-8), false);
                                    }
                                }

                                double progress = (double) (i + 1) / (double) rectList.getItems().size();
                                progBar.setProgress(progress);
                                if (!(i == rectList.getItems().size() - 1) || repeatCheckBox.isSelected()) {
                                    robot.delay(Utilities.getRandomNumberInRange((1000 * secondDelay.getValue()) - 7, (1000 * secondDelay.getValue()) + 7));
                                }

                            } else {
                                return;
                            }
                        }
                    } while(repeatCheckBox.isSelected());
                    System.out.println("Finished Running");
                    progBar.setProgress(0);
                    return;
                } else {
                    System.out.println("No rectangles set.");
                    return;
                }
            }
        });
        botThread.start();
    }

    public void addTextButtonClicked() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Enter Your Text (Cannot Include Square Brackets)");
        td.setHeaderText(null);
        HBox hBox = new HBox();
        CheckBox checkBox = new CheckBox("Press Enter After Text");
        TextField textField = new TextField();
        hBox.getChildren().addAll(textField, checkBox);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        td.getDialogPane().setContent(hBox);
        td.showAndWait();

        if(textField.getText().equals("") && checkBox.isSelected() == false) {
            return;
        }

        if(rectList.getItems().get(0) == null) {
            rectList.getItems().clear();
        }

        if(checkBox.isSelected() == true) {
            rectList.getItems().add(textField.getText() + " /Enter/");
        } else {
            rectList.getItems().add(textField.getText());
        }
    }

    public void stopButtonClicked() {
        System.out.println("Stopped Running");
        running = false;
        if(botThread != null) {
            botThread.interrupt();
        }
        progBar.setProgress(0);
    }

    class ScreenCaptureRectangle {
        private Rectangle captureRect;

        ScreenCaptureRectangle(final BufferedImage screen) {

            BufferedImage screenCopy = new BufferedImage(screen.getWidth(),
                    screen.getHeight(), screen.getType());
            JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
            JScrollPane screenScroll = new JScrollPane(screenLabel);

            screenScroll.setPreferredSize(new Dimension(800, 800));

            repaint(screen, screenCopy);
            screenLabel.repaint();

            screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
                Point start = new Point();

                @Override
                public void mouseMoved(MouseEvent me) {
                    start = me.getPoint();
                    repaint(screen, screenCopy);
                    screenLabel.repaint();
                }

                @Override
                public void mouseDragged(MouseEvent me) {
                    Point end = me.getPoint();
                    captureRect = new Rectangle(start, new Dimension(end.x
                            - start.x, end.y - start.y));
                    repaint(screen, screenCopy);
                    screenLabel.repaint();
                }
            });
            // JOptionPane.showMessageDialog(null, screenScroll);

            final JDialog dialog = new JDialog();
            Window window = dialog.getOwner();
            dialog.setAlwaysOnTop(true);

            int input = JOptionPane.showOptionDialog(dialog, screenScroll,
                    "Region Select", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);

            if (input == JOptionPane.OK_OPTION) {
                System.out.println("Region Selected");
                window.dispose();
            } else {
                System.out.println("Region not selected... Exiting...");
                captureRect = null;
                window.dispose();
            }
        }

        public void repaint(BufferedImage orig, BufferedImage copy) {
            Graphics2D g = copy.createGraphics();
            g.drawImage(orig, 0, 0, null);
            g.setColor(Color.RED);
            if (captureRect == null) {
                return;
            }
            g.draw(captureRect);
            g.setColor(new Color(25, 25, 23, 10));
            g.fill(captureRect);
            g.dispose();
        }

        public Rectangle getRect() {
            return captureRect;
        }
    }
}