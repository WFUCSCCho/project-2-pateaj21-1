import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;
    try {
        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line (header)
        inputFileNameScanner.nextLine();

        // Read data into ArrayList
        ArrayList<Taylor_Discography_Data> records = new ArrayList<>();
        for (int i = 0; i < numLines && inputFileNameScanner.hasNextLine(); i++) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split CSV while respecting quotes

            // Parse the line into a Taylor_Discography_Data object
            String album = parts[0].trim();
            String track_title = parts[1].trim();
            int track_number = Integer.parseInt(parts[2].trim());
            String released_year = parts[3].trim();
            String lyrics = parts[4].trim();
            String writers = parts[5].trim();
            String spotifyId = parts[6].trim();

            records.add(new Taylor_Discography_Data(album, track_title, track_number,
                    released_year, lyrics, writers, spotifyId));
        }
        // Create sorted and randomized copies
        ArrayList<Taylor_Discography_Data> sortedRecords = new ArrayList<>(records);
        ArrayList<Taylor_Discography_Data> randomizedRecords = new ArrayList<>(records);
        Collections.sort(sortedRecords);
        Collections.shuffle(randomizedRecords);

        // Create BST and AVL trees
        BST<Taylor_Discography_Data> sortedBST = new BST<>();
        BST<Taylor_Discography_Data> randomBST = new BST<>();
        AvlTree<Taylor_Discography_Data> sortedAVL = new AvlTree<>();
        AvlTree<Taylor_Discography_Data> randomAVL = new AvlTree<>();

        // Time insertions
        long startTime, endTime;

        // Sorted BST insertion
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : sortedRecords) {
            sortedBST.insert(record);
        }
        endTime = System.nanoTime();
        double sortedBSTInsertTime = (endTime - startTime) / 1_000_000_000.0;

        // Random BST insertion
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : randomizedRecords) {
            randomBST.insert(record);
        }
        endTime = System.nanoTime();
        double randomBSTInsertTime = (endTime - startTime) / 1_000_000_000.0;

        // Sorted AVL insertion
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : sortedRecords) {
            sortedAVL.insert(record);
        }
        endTime = System.nanoTime();
        double sortedAVLInsertTime = (endTime - startTime) / 1_000_000_000.0;

        // Random AVL insertion
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : randomizedRecords) {
            randomAVL.insert(record);
        }
        endTime = System.nanoTime();
        double randomAVLInsertTime = (endTime - startTime) / 1_000_000_000.0;

        // Time searches
        // Sorted BST search
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : records) {
            sortedBST.search(record);
        }
        endTime = System.nanoTime();
        double sortedBSTSearchTime = (endTime - startTime) / 1_000_000_000.0;

        // Random BST search
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : records) {
            randomBST.search(record);
        }
        endTime = System.nanoTime();
        double randomBSTSearchTime = (endTime - startTime) / 1_000_000_000.0;

        // Sorted AVL search
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : records) {
            sortedAVL.contains(record);
        }
        endTime = System.nanoTime();
        double sortedAVLSearchTime = (endTime - startTime) / 1_000_000_000.0;

        // Random AVL search
        startTime = System.nanoTime();
        for (Taylor_Discography_Data record : records) {
            randomAVL.contains(record);
        }
        endTime = System.nanoTime();
        double randomAVLSearchTime = (endTime - startTime) / 1_000_000_000.0;

        // Print results to console in a formatted way
        System.out.println("Number of records: " + numLines);
        System.out.println("\nInsertion Times (seconds):");
        System.out.printf("Sorted BST:" + "%.6f\n", sortedBSTInsertTime);
        System.out.printf("Random BST" + "%.6f\n", randomBSTInsertTime);
        System.out.printf("Sorted AVL:" + "%.6f\n", sortedAVLInsertTime);
        System.out.printf("Random AVL:" + "%.6f\n", randomAVLInsertTime);

        System.out.println("\nSearch Times (seconds):");
        System.out.printf("%.6f\n", sortedBSTSearchTime);
        System.out.printf("%.6f\n", randomBSTSearchTime);
        System.out.printf("%.6f\n", sortedAVLSearchTime);
        System.out.printf("%.6f\n", randomAVLSearchTime);

        // Append results to output.txt in CSV format
        try (FileWriter fw = new FileWriter("output.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Write CSV header if file is empty
            File outputFile = new File("output.txt");
            if (outputFile.length() == 0) {
                out.println("N, SortedBST_Insert, RandomBST_Insert, SortedAVL_Insert, RandomAVL_Insert," +
                        "SortedBST_Search, RandomBST_Search, SortedAVL_Search, RandomAVL_Search");
            }
            // Write results
            out.printf("%d,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f\n",
                    numLines,
                    sortedBSTInsertTime, randomBSTInsertTime,
                    sortedAVLInsertTime, randomAVLInsertTime,
                    sortedBSTSearchTime, randomBSTSearchTime,
                    sortedAVLSearchTime, randomAVLSearchTime);
        }

    } finally {
        // Close the input file
        if (inputFileNameScanner != null) {
            inputFileNameScanner.close();
        }
        if (inputFileNameStream != null) {
            inputFileNameStream.close();

        }
      }
   }
}