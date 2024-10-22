package com.project.projects;

import java.util.*;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainUI extends Application {
    private boolean is_lpt = false;
    private Text result = new Text();

    private List<TextField> textFields = new ArrayList<>();
    private ArrayList<int[]> taskSets = new ArrayList<>();
    private ArrayList<int[]> periods = new ArrayList<>();

    private int[] processors;
    ChoiceBox<String> algoCB;
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #334b6d;");

        // Algorithm Settings layout
        Pane algoGroup = new Pane();
        algoGroup.setPrefSize(450, 160);
        algoGroup.setLayoutY(50);
        algoGroup.setLayoutX(30);
        algoGroup.setStyle("-fx-background-color: #1d2637;");

        Text algoG_txt1 = new Text(15, 30, "Algorithm Settings");
        algoG_txt1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        algoG_txt1.setFill(Color.web("#c0c3c8"));
        Text algoG_txt2 = new Text(40, 70, "Choose an Algorithm");
        algoG_txt2.setFill(Color.web("#c0c3c8"));
        Text algoG_txt3 = new Text(240, 70, "Choose number of processors");
        algoG_txt3.setFill(Color.web("#c0c3c8"));

        algoCB = new ChoiceBox<>();
        algoCB.setLayoutX(35);
        algoCB.setLayoutY(85);
        algoCB.getItems().addAll("SB Algorithm", "RI Algorithm", "FP Algorithm", "FP-MS Algorithm", "IPB Algorithm");

        Image info = new Image("file:C:/Users/HP/Downloads/info.png");
        ImageView iv = new ImageView(info);
        iv.setPreserveRatio(true);
        iv.setFitWidth(25);
        iv.setLayoutX(170);
        iv.setLayoutY(85);

        Tooltip tooltip = new Tooltip("SB — Separated Blocks\nRI — Reverse-Interference based\nFP — Fictitious-Processes based\n"
                +"FP-MS — Fictitious-Processes with Multi-Subset based\nIPB — Interference Processes-Blocks based");
        tooltip.setStyle("-fx-font-size: 16px;");
        tooltip.setShowDuration(Duration.INDEFINITE); // Tooltip will stay visible as long as the cursor is over the image
        Tooltip.install(iv, tooltip);

        ChoiceBox<String> numCB = new ChoiceBox<>();
        numCB.setLayoutX(250);
        numCB.setLayoutY(85);
        numCB.setPrefWidth(100);
        numCB.getItems().addAll("2","3","4","5");

        ToggleGroup heuristicG = new ToggleGroup();
        RadioButton sptRB = new RadioButton("SPT");
        RadioButton lptRB = new RadioButton("LPT");
        sptRB.setStyle("-fx-text-fill: #c0c3c8;");
        lptRB.setStyle("-fx-text-fill: #c0c3c8;");
        sptRB.setLayoutX(40);
        sptRB.setLayoutY(128);
        lptRB.setLayoutX(120);
        lptRB.setLayoutY(128);
        sptRB.setToggleGroup(heuristicG);
        lptRB.setToggleGroup(heuristicG);

        algoGroup.getChildren().addAll(algoG_txt1, algoG_txt2, algoG_txt3, algoCB,iv,numCB, sptRB, lptRB);

////////////////////////////////////////////////////////////////////////////////////////////

        // Instances Settings layout
        Pane instanceGroup = new Pane();
        instanceGroup.setPrefSize(450, 400);
        instanceGroup.setLayoutY(220);
        instanceGroup.setLayoutX(30);
        instanceGroup.setStyle("-fx-background-color: #1d2637;");

        Text instanceG_txt = new Text(15, 30, "Instances Settings");
        instanceG_txt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        instanceG_txt.setFill(Color.web("#c0c3c8"));

        Text chooseInstanceTxt = new Text(40, 70, "Choose an Instance");
        chooseInstanceTxt.setFill(Color.web("#c0c3c8"));

        Text enterNumOfP_txt = new Text(240, 55, "Enter number of processes and\nnumber of proc. times:");
        enterNumOfP_txt.setFill(Color.web("#c0c3c8"));

        ChoiceBox<String> instanceCB = new ChoiceBox<>();
        instanceCB.setLayoutX(45);
        instanceCB.setLayoutY(85);
        instanceCB.setValue("Instance 1");
        instanceCB.getItems().addAll("Instance 1");

        TextField numberOfP_TF = new TextField();
        numberOfP_TF.setPrefWidth(50);
        numberOfP_TF.setLayoutX(240);
        numberOfP_TF.setLayoutY(85);


        TextField numberOfBT_TF = new TextField();
        numberOfBT_TF.setPrefWidth(50);
        numberOfBT_TF.setLayoutX(300);
        numberOfBT_TF.setLayoutY(85);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefSize(395, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setStyle("-fx-background-color: #1d2637;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setPannable(true);
        scrollPane.setPrefSize(410, 200);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(140);

        Button addButton = new Button("Add");
        addButton.setPrefWidth(50);
        addButton.setLayoutX(360);
        addButton.setLayoutY(85);
        addButton.setOnAction(e -> {
            gridPane.getChildren().clear(); // Clear the previous text fields
            textFields.clear(); // Clear the list that tracks all TextFields

            try {
                // Parse the input numbers
                int numberOfProcesses = Integer.parseInt(numberOfP_TF.getText());
                int numberOfFieldsPerRow = Integer.parseInt(numberOfBT_TF.getText());

                // Add rows based on numberOfP_TF
                for (int i = 0; i < numberOfProcesses; i++) {
                    // First element in the row is a Text label (P1, P2, etc.)
                    Text rowLabel = new Text("P" + (i + 1));
                    rowLabel.setFill(Color.web("#c0c3c8"));
                    gridPane.add(rowLabel, 0, i);

                    // Add TextFields based on numberOfBT_TF
                    for (int j = 0; j < numberOfFieldsPerRow; j++) {
                        TextField tf = new TextField();
                        tf.setPromptText("Time " + (j + 1));
                        textFields.add(tf); // Add to the list of fields
                        gridPane.add(tf, j + 1, i); // Position after the label
                    }
                }
            } catch (NumberFormatException ex) {
                numberOfP_TF.setText("");
                numberOfP_TF.setPromptText("Invalid input.");
                numberOfBT_TF.setText("");
                numberOfBT_TF.setPromptText("Invalid input.");
            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setPrefWidth(70);
        clearButton.setLayoutX(23);
        clearButton.setLayoutY(350);
        clearButton.setOnAction(e -> {
            numberOfP_TF.setText("");
            numberOfBT_TF.setText("");
            gridPane.getChildren().clear();
            textFields.clear();
        });

        instanceGroup.getChildren().addAll(
                instanceG_txt, chooseInstanceTxt, enterNumOfP_txt, numberOfP_TF, numberOfBT_TF, addButton, scrollPane,
                clearButton, instanceCB
        );


////////////////////////////////////////////////////////////////////////////////////////////

        // Results Section layout
        Pane resultsGroup = new Pane();
        resultsGroup.setPrefSize(215, 160);
        resultsGroup.setLayoutY(50);
        resultsGroup.setLayoutX(500);
        resultsGroup.setStyle("-fx-background-color: #1d2637;");

        Text resultsTxt = new Text(15, 30, "Results");
        resultsTxt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        resultsTxt.setFill(Color.web("#c0c3c8"));
        //result = new Text();
        result.setLayoutX(80);
        result.setLayoutY(95);
        result.setFont(Font.font(35));

        Rectangle resultBox = new Rectangle(50, 60, 115, 50);
        resultBox.setFill(Color.WHITE);
        resultBox.setStroke(Color.BLACK);

        Button calculateBtn = new Button("Calculate Cmax");
        calculateBtn.setLayoutX(55);
        calculateBtn.setLayoutY(120);
        calculateBtn.setOnAction(e ->{
            taskSets.clear(); // Clear any previous data
            String selectedNum = numCB.getValue();
            RadioButton selected_h = (RadioButton) heuristicG.getSelectedToggle();
            int numPr = Integer.parseInt(selectedNum); //Number of processors chosen by user.
            int numberOfProcesses = Integer.parseInt(numberOfP_TF.getText());
            int numberOfFieldsPerRow = Integer.parseInt(numberOfBT_TF.getText());


            // Iterate over each column (field per row)
            for (int col = 1; col <= numberOfFieldsPerRow; col++) {
                int[] columnValues = new int[numberOfProcesses];  // Array for each column

                for (int row = 0; row < numberOfProcesses; row++) {
                    TextField tf = (TextField) getNodeByRowColumnIndex(row, col, gridPane);  // Get the TextField in the specific row/col
                    try {
                        columnValues[row] = Integer.parseInt(tf.getText());  // Store the integer value
                    } catch (NumberFormatException ex) {
                        columnValues[row] = 0;  // Default to 0 if the input is invalid
                    }
                }
                taskSets.add(columnValues);  // Add the array to the taskSets list
            }
            int numTaskSets = taskSets.size();

            if(algoCB.getValue().equals("SB Algorithm") && selected_h.getText().equals("SPT")) {
                is_lpt = false;
                result.setText(processTaskSets_Algo1(taskSets, numTaskSets, numPr)+"s");
            }
            else if(algoCB.getValue().equals("SB Algorithm") && selected_h.getText().equals("LPT")){
                is_lpt = true;
                result.setText(processTaskSets_Algo1(taskSets, numTaskSets, numPr)+"s");
            }
            else if(algoCB.getValue().equals("RI Algorithm") && selected_h.getText().equals("SPT")) {
                is_lpt = false;
                result.setText(processTaskSets_Algo2(taskSets, numTaskSets, numPr)+"s");
            }
            else if(algoCB.getValue().equals("RI Algorithm") && selected_h.getText().equals("LPT")) {
                is_lpt = true;
                result.setText(processTaskSets_Algo2(taskSets, numTaskSets, numPr)+"s");
            }
        });
        resultsGroup.getChildren().addAll(resultsTxt, resultBox, result, calculateBtn);

////////////////////////////////////////////////////////////////////////////////////////////

        // Graph Section (To be implemented later)
        Pane graphGroup = new Pane();
        graphGroup.setPrefSize(600, 400);
        graphGroup.setLayoutY(220);
        graphGroup.setLayoutX(500);
        graphGroup.setStyle("-fx-background-color: #1d2637;");

        Text graphTxt = new Text(15, 30, "Graph");
        graphTxt.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        graphTxt.setFill(Color.web("#c0c3c8"));

        Button displayGraphBtn = new Button("Display Graph");
        displayGraphBtn.setLayoutX(150);
        displayGraphBtn.setLayoutY(180);
        displayGraphBtn.setStyle("-fx-font-size: 30px;");
        displayGraphBtn.setPrefSize(300, 20);
        displayGraphBtn.setOnAction(e ->{
            displayGraph();
        });





        graphGroup.getChildren().addAll(graphTxt,displayGraphBtn);

////////////////////////////////////////////////////////////////////////////////////////////

        // Set up the main scene and stage
        root.getChildren().addAll(algoGroup,instanceGroup,resultsGroup,graphGroup);
        Scene scene = new Scene(root, 1220, 590);
        primaryStage.setTitle("Algorithms");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Dynamic method to process task sets
    private int processTaskSets_Algo2(ArrayList<int[]> taskSets, int numTaskSets, int numProcessors) {
        periods.clear(); // Clear any previous data

        // Loop over each task set and call H_Algo2() method to schedule
        for (int i = 0; i < numTaskSets; i++) {
            H_Algo2(taskSets.get(i), numProcessors);
        }

        // Sort and reverse each array in "periods".
        for (int i = 0; i < periods.size(); i++) {
            if (i % 2 == 0) {
                // If index is even, sort the array
                Arrays.sort(periods.get(i));
            } else {
                // If index is odd, reverse the array
                reverseArray(periods.get(i));
            }
        }

        // Calculate Cmax
        int[] toGet_Cmax = new int[numProcessors];
        for (int i = 0; i < numProcessors; i++) {
            for (int[] period : periods) {
                toGet_Cmax[i] += period[i]; // Summing up the loads
            }
        }
        return Arrays.stream(toGet_Cmax).max().getAsInt();
    }



    private int processTaskSets_Algo1(ArrayList<int[]> taskSets, int numTaskSets, int numProcessors) {
        int total_cmax = 0; // Initialize total Cmax

        // Process each task set dynamically
        for (int i = 0; i < numTaskSets; i++) {
            int cmax = H_Algo1(taskSets.get(i), numProcessors); // Get Cmax for each task set
            total_cmax += cmax; }
        return total_cmax;
    }



    private void H_Algo2(int[] processes, int numProcessors) {
        Arrays.sort(processes); // Sort tasks

        if(is_lpt)
            reverseArray(processes);

        processors = new int[numProcessors]; // Initialize processors
        for (int process : processes) {
            allocateProcess(processors, process); // Assign tasks to processors
        }

        // Store the processor loads in periods list
        periods.add(Arrays.copyOf(processors, numProcessors));
    }



    //The below function returns the cmax value of a single period, it takes an array as an argument and an
    //int number that represents the number of processors.
    private int H_Algo1(int[] processes, int numProcessors){
        Arrays.sort(processes); //Arrays.sort sorts in ascending order by default.

        if(is_lpt)
            reverseArray(processes);

        processors = new int[numProcessors]; //Keeps track of the load on each processor
        for (int process : processes) {
            allocateProcess(processors, process);   //Each process is allocated to the processor with the least load using allocateProcess() method
        }
        return Arrays.stream(processors).max().getAsInt(); //Returns the Cmax which is the maximum load among all processors.
    }



    //The method below allocates a process to the least loaded processor (finds the index of the processor with the minimum load, then
    //adds the process to that processor's load)
    public void allocateProcess(int[] processors, int process) {
        int min = 0;
        for (int i = 1; i < processors.length; i++) {
            if (processors[i] < processors[min]) {
                min = i;
            }
        }
        processors[min] += process;
    }


    private void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // Method to display the Gantt chart (task distribution using Rectangle)
// Method to display the Gantt chart (task distribution using Rectangle)
    private void displayGraph() {
        Stage graphStage = new Stage();
        graphStage.setTitle("Task Distribution Among Processors");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Pane chartPane = new Pane();
        chartPane.setPrefSize(800, 200);

        double barHeight = 40;
        double spacing = 10;
        double maxWidth = 700; // Maximum width for the bars

        // Define processors data based on the chosen algorithm
        int[][] processorsData;
        if (algoCB.getValue().equals("SB Algorithm")) {
            processorsData = getProcessorData_SB(); // Generate separated blocks
        } else if (algoCB.getValue().equals("RI Algorithm")) {
            processorsData = getProcessorData_RI(); // Generate reverse interference layout
        } else {
            // Handle the case where neither SB nor RI is selected
            processorsData = new int[0][0];  // Empty data
        }

        // Colors for tasks
        Color[] taskColors = {
                Color.rgb(220, 53, 69),  // Red
                Color.rgb(108, 117, 125), // Gray
                Color.rgb(255, 193, 7),   // Yellow
                Color.rgb(40, 167, 69),   // Green
                Color.rgb(0, 123, 255),   // Blue
                Color.rgb(111, 66, 193),  // Purple
                Color.rgb(23, 162, 184)   // Cyan
        };

        // Calculate the maximum total duration
        int maxTotalDuration = Arrays.stream(processorsData)
                .mapToInt(processor -> Arrays.stream(processor).sum())
                .max()
                .orElse(0);

        // Generate bars for each processor
        for (int i = 0; i < processorsData.length; i++) {
            int[] processorTasks = processorsData[i];

            // Add processor label
            Label processorLabel = new Label("Processor " + (processorsData.length - i));
            processorLabel.setLayoutX(0);
            processorLabel.setLayoutY(i * (barHeight + spacing) + barHeight / 2);

            double xOffset = 100; // Start after the label
            for (int j = 0; j < processorTasks.length; j++) {
                int duration = processorTasks[j];
                double width = (duration / (double) maxTotalDuration) * maxWidth;

                Rectangle rect = new Rectangle(width, barHeight);
                rect.setX(xOffset);
                rect.setY(i * (barHeight + spacing));
                rect.setFill(taskColors[j % taskColors.length]);

                Text taskText = new Text("P" + (j + 1));
                taskText.setX(xOffset + width / 2 - taskText.getBoundsInLocal().getWidth() / 2);
                taskText.setY(i * (barHeight + spacing) + barHeight / 2 + 5);
                taskText.setFill(Color.WHITE);

                chartPane.getChildren().addAll(rect, taskText);
                xOffset += width;
            }

            chartPane.getChildren().add(processorLabel);
        }

        // Create x-axis
        NumberAxis xAxis = new NumberAxis(0, maxTotalDuration, 20);
        xAxis.setLabel("Load (Processing Time)");
        xAxis.setLayoutY(processorsData.length * (barHeight + spacing));
        xAxis.setPrefWidth(maxWidth);
        xAxis.setLayoutX(100); // Align with the start of the bars
        chartPane.getChildren().add(xAxis);

        // Add color legend (bottom)
        HBox legend = new HBox(10);
        legend.setPadding(new Insets(20, 0, 0, 100));
        for (int i = 0; i < taskColors.length; i++) {
            Rectangle colorBox = new Rectangle(20, 20);
            colorBox.setFill(taskColors[i]);

            Label taskLabel = new Label("P" + (i + 1));
            taskLabel.setTextFill(Color.BLACK);

            VBox taskLegend = new VBox(colorBox, taskLabel);
            legend.getChildren().add(taskLegend);
        }

        root.getChildren().addAll(chartPane, legend);

        Scene scene = new Scene(root, 900, 400);
        graphStage.setScene(scene);
        graphStage.show();
    }

    // Method to generate processor data for SB Algorithm (Separated Blocks)
    private int[][] getProcessorData_SB() {
        return taskSets.toArray(new int[0][0]);
    }

    // Method to generate processor data for RI Algorithm (Reverse Interference)
    private int[][] getProcessorData_RI() {
        return periods.toArray(new int[0][0]);
    }


//    private void displayGraph() {
//        Stage graphStage = new Stage();
//        graphStage.setTitle("Task Distribution Among Processors");
//
//        VBox root = new VBox(10);
//        root.setPadding(new Insets(20));
//        root.setStyle("-fx-background-color: white;");
//
//        // Create the chart
//        Pane chartPane = new Pane();
//        chartPane.setPrefSize(800, 200);
//
//        double barHeight = 40;
//        double spacing = 10;
//        double maxWidth = 700; // Maximum width for the bars
//
//        // Assuming we have the data in the 'processors' array
//        // Each inner array represents a processor and contains the durations of its tasks
//        int[][] processorsData = {
//                {15, 10, 20, 15, 20, 20},       // Processor 3
//                {10, 30, 25, 5, 15, 10, 15},    // Processor 2
//                {20, 10, 5, 20, 20, 15, 20}     // Processor 1
//        };
//
//        // Calculate the maximum total duration
//        int maxTotalDuration = Arrays.stream(processorsData)
//                .mapToInt(processor -> Arrays.stream(processor).sum())
//                .max()
//                .orElse(0);
//
//        for (int i = 0; i < processorsData.length; i++) {
//            int[] processorTasks = processorsData[i];
//
//            // Add processor label
//            Label processorLabel = new Label("Processor " + (processorsData.length - i));
//            processorLabel.setLayoutX(0);
//            processorLabel.setLayoutY(i * (barHeight + spacing) + barHeight / 2);
//
//            double xOffset = 100; // Start after the label
//            for (int j = 0; j < processorTasks.length; j++) {
//                int duration = processorTasks[j];
//                double width = (duration / (double) maxTotalDuration) * maxWidth;
//
//                Rectangle rect = new Rectangle(width, barHeight);
//                rect.setX(xOffset);
//                rect.setY(i * (barHeight + spacing));
//                rect.setFill(getColorForTask(j));
//
//                Text taskText = new Text("P" + (j + 1));
//                taskText.setX(xOffset + width / 2 - taskText.getBoundsInLocal().getWidth() / 2);
//                taskText.setY(i * (barHeight + spacing) + barHeight / 2 + 5);
//                taskText.setFill(Color.WHITE);
//
//                chartPane.getChildren().addAll(rect, taskText);
//                xOffset += width;
//            }
//
//            chartPane.getChildren().add(processorLabel);
//        }
//
//        // Create x-axis
//        NumberAxis xAxis = new NumberAxis(0, maxTotalDuration, 20);
//        xAxis.setLabel("Load (Processing Time)");
//        xAxis.setLayoutY(processorsData.length * (barHeight + spacing));
//        xAxis.setPrefWidth(maxWidth);
//        xAxis.setLayoutX(100); // Align with the start of the bars
//        chartPane.getChildren().add(xAxis);
//
//        root.getChildren().add(chartPane);
//
//        Scene scene = new Scene(root, 900, 300);
//        graphStage.setScene(scene);
//        graphStage.show();
//    }

    private Color getColorForTask(int taskIndex) {
        Color[] colors = {
                Color.rgb(220, 53, 69),   // Red
                Color.rgb(108, 117, 125), // Gray
                Color.rgb(255, 193, 7),   // Yellow
                Color.rgb(40, 167, 69),   // Green
                Color.rgb(0, 123, 255),   // Blue
                Color.rgb(111, 66, 193),  // Purple
                Color.rgb(23, 162, 184)   // Cyan
        };
        return colors[taskIndex % colors.length];
    }
    // Helper class to store task information
    private static class TaskInfo {
        int periodIndex;
        int processorIndex;
        int duration;

        TaskInfo(int periodIndex, int processorIndex, int duration) {
            this.periodIndex = periodIndex;
            this.processorIndex = processorIndex;
            this.duration = duration;
        }
    }

    // Helper method to generate a random color for the processes

//    private void displayGraph() {
//        Stage graphStage = new Stage();
//        graphStage.setTitle("Gantt Chart - Task Distribution");
//
//        VBox ganttChartPane = new VBox(10);  // Vertical box for processors/periods
//        ganttChartPane.setAlignment(Pos.CENTER_LEFT);
//        ganttChartPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
//
//        // Check if we are using "RI Algorithm" or other algorithms
//        boolean isRIAlgorithm = algoCB.getValue().equals("RI Algorithm");
//
//        // Get the data source (processors or periods) based on the selected algorithm
//        List<int[]> dataSource = isRIAlgorithm ? periods : Arrays.asList(processors);  // Use Arrays.asList to wrap processors array into a list
//
//        // Loop through processors/periods to generate the Gantt chart
//        for (int i = 0; i < dataSource.size(); i++) {
//            int[] currentData = dataSource.get(i);
//
//            // Create a horizontal box for each processor/period timeline
//            HBox processorBox = new HBox(5);  // Horizontal box for each processor timeline
//            processorBox.setAlignment(Pos.CENTER_LEFT);
//            processorBox.setStyle("-fx-border-color: gray; -fx-border-width: 0 0 1 0;");
//            processorBox.setPrefHeight(50);
//
//            // Add a label for each processor/period
//            Label processorLabel = new Label((isRIAlgorithm ? "Period " : "Processor ") + (i + 1) + ": ");
//            processorLabel.setMinWidth(100);
//            processorBox.getChildren().add(processorLabel);
//
//            // For each load (process/task), create a rectangle in the Gantt chart
//            for (int j = 0; j < currentData.length; j++) {
//                Rectangle rect = new Rectangle(currentData[j] * 10, 40);  // Width proportional to load time
//                rect.setFill(getRandomColor());
//
//                // Label for the process information (P1, P2, etc.)
//                Label processLabel = new Label("P" + (j + 1) + " (" + currentData[j] + ")");
//                processLabel.setTextFill(Color.WHITE);
//
//                StackPane processPane = new StackPane();
//                processPane.getChildren().addAll(rect, processLabel);
//                processPane.setAlignment(Pos.CENTER);
//
//                // Add the process rectangle to the processor timeline
//                processorBox.getChildren().add(processPane);
//            }
//
//            // Add the processor/period timeline to the main Gantt chart pane
//            ganttChartPane.getChildren().add(processorBox);
//        }
//
//        // Set up the scene and display the graph
//        VBox vbox = new VBox(ganttChartPane);
//        Scene scene = new Scene(vbox, 800, 600);
//        graphStage.setScene(scene);
//        graphStage.show();
//    }

    // Helper method to generate a random color for the processes
    private Color getRandomColor() {
        Random random = new Random();
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }



    // Utility function to get the node by row and column index
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Application.launch(args);
    }

}


