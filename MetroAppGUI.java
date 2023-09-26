import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class MetroAppGUI {
    private JFrame frame;
    private JComboBox<String> sourceStationComboBox;
    private JComboBox<String> destinationStationComboBox;
    private JTextArea resultTextArea;

    private Graph_M metroGraph;

    public MetroAppGUI(Graph_M graph) {
        metroGraph = graph;

        frame = new JFrame("Metro App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        try {
            ImageIcon logoIcon = new ImageIcon(ImageIO.read(getClass().getResource("Delhi_Metro_logo.svg.png")));
            Image scaledImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            titlePanel.add(logoLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Metro App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titlePanel.add(titleLabel);
        Font labelFont = new Font("SansSerif", Font.BOLD, 32); 

        JPanel stationPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel sourceLabel = new JLabel("Source Station:");
        stationPanel.add(sourceLabel);

        sourceStationComboBox = new JComboBox<>(getStationNames());
        stationPanel.add(sourceStationComboBox);

        JLabel destinationLabel = new JLabel("Destination Station:");
        stationPanel.add(destinationLabel);

        destinationStationComboBox = new JComboBox<>(getStationNames());
        stationPanel.add(destinationStationComboBox);
        sourceLabel.setFont(labelFont);
        destinationLabel.setFont(labelFont);
        sourceLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        destinationLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton findShortestDistanceButton = new JButton("Find Shortest Distance");
        JButton findShortestTimeButton = new JButton("Find Shortest Time");
        JButton shortestPathDistanceButton = new JButton("Shortest Path Distance"); 
        buttonPanel.add(findShortestDistanceButton);
        buttonPanel.add(findShortestTimeButton);
        buttonPanel.add(shortestPathDistanceButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        resultTextArea = new JTextArea(5, 20);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(labelFont);
        panel.add(titlePanel);
        panel.add(stationPanel);
        panel.add(buttonPanel);
        panel.add(new JScrollPane(resultTextArea));

        findShortestDistanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = (String) sourceStationComboBox.getSelectedItem();
                String destination = (String) destinationStationComboBox.getSelectedItem();

                if (source != null && destination != null) {
                    int distance = metroGraph.dijkstra(source, destination, false);
                    resultTextArea.setText("Shortest Distance: " + distance + " KM");
                }
            }
        });

        findShortestTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = (String) sourceStationComboBox.getSelectedItem();
                String destination = (String) destinationStationComboBox.getSelectedItem();

                if (source != null && destination != null) {
                    int time = metroGraph.dijkstra(source, destination, true);
                    resultTextArea.setText("Shortest Time: " + time + " minutes");
                }
            }
        });

        shortestPathDistanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = (String) sourceStationComboBox.getSelectedItem();
                String destination = (String) destinationStationComboBox.getSelectedItem();

                if (source != null && destination != null) {
                    ArrayList<String> shortestPathList = metroGraph.getShortestDistancePath(source, destination);
                    if (shortestPathList != null) {
                        String shortestPath = String.join(" -> ", shortestPathList);
                        resultTextArea.setText("Shortest Path Distance:\n" + shortestPath);
                    } else {
                        resultTextArea.setText("No path found.");
                    }
                                    }
            }
        });



        frame.add(panel);
        frame.setVisible(true);
    }

    private String[] getStationNames() {
        ArrayList<String> stationList = new ArrayList<>(metroGraph.vtces.keySet());
        return stationList.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Graph_M metroGraph = new Graph_M();
        Graph_M.Create_Metro_Map(metroGraph);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MetroAppGUI(metroGraph);
            }
        });
    }
}
