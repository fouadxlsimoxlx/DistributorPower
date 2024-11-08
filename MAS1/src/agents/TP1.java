package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class TP1 extends Agent {

    private Map<String, Boolean> fileDictionary = new HashMap<>();
    private Set<String> wordDictionary = new HashSet<>();
    private int totalFilesScanned = 0;
    private int infectedFiles = 0; // 
    private int correctFiles = 0; // 

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        /*String dictionaryPath = "C:\\Users\\dmc\\eclipse-workspace\\MAS1\\dictionary.txt"; 
        loadDictionary(dictionaryPath);

        String directoryPath = "C:\\Users\\dmc\\eclipse-workspace\\MAS1\\Test"; 
        loadTextFilesFromDirectory(directoryPath);

        for (String filePath : fileDictionary.keySet()) {
            scanFile(filePath);
        }
        
        // Display overall statistics after scanning
        displayOverallStatistics();
        
        System.out.println("Completed scanning files. Agent will terminate.");
        */
        
        Send_Data();
        doDelete(); 
    }
    
    private void Send_Data() {
    	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        // Set the receiver of the message
        msg.addReceiver(new AID("SecondAgent", AID.ISLOCALNAME));

        // Set the content of the message
        msg.setContent("Hello, this is First Agent");

        // Send the message
        send(msg);

        // Print confirmation
        System.out.println("Message sent to SecondAgent: " + msg.getContent());
    	
    }
    
    
    
    
    
    
    
    private void loadDictionary(String dictionaryPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordDictionary.add(line.trim().toLowerCase()); 
            }
            System.out.println("Dictionary loaded with " + wordDictionary.size() + " words.\n");
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }
    }

    // Load all text files from the specified directory into the fileDictionary
    private void loadTextFilesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                fileDictionary.put(file.getAbsolutePath(), false); // Add the file path to the dictionary
            }
            System.out.println("Loaded " + fileDictionary.size() + " text files from directory: " + directoryPath);
        } else {
            System.err.println("Error accessing directory: " + directoryPath);
        }
    }

    // Scan a file to check if it contains any words from the dictionary
    private void scanFile(String filePath) {
        totalFilesScanned++;

        // Check if the file contains any words from the dictionary
        FileScanResult result = checkFileForWords(filePath);

        // Update counters based on results
        if (result.correctWordsCount > 0) {
            correctFiles++; // Count file as correct if it has correct words
        }
        if (result.wrongWordsFound.size() > 0) {
            infectedFiles++; // Count file as infected if it has wrong words
        }

        // Display the percentage and list of correct and incorrect words
        displayFileScanResult(result , filePath);
    }

    // Helper class to store the result of a file scan
    private static class FileScanResult {
        int totalWords = 0;
        int correctWordsCount = 0;
        Set<String> correctWordsFound = new HashSet<>();
        Set<String> wrongWordsFound = new HashSet<>();
    }

    // Check if a file contains any of the words from the dictionary and return the result
    private FileScanResult checkFileForWords(String filePath) {
        FileScanResult result = new FileScanResult();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split line into words
                result.totalWords += words.length;

                for (String word : words) {
                    String cleanedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Clean and lowercase
                    if (wordDictionary.contains(cleanedWord)) {
                        result.wrongWordsFound.add(cleanedWord); // Add to wrong words
                    } else {
                        result.correctWordsCount++;
                        result.correctWordsFound.add(cleanedWord); // Add to correct words
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
        return result;
    }

    // Display the percentage of correct words and list correct/incorrect words for the file
    private void displayFileScanResult(FileScanResult result, String filePath) {
        if (result.totalWords == 0) {
            System.out.println("No words found in file: " + filePath);
            return;
        }

        double percentage = (double) result.correctWordsCount / result.totalWords * 100;

        // Extract just the filename from the filePath
        String fileName = new File(filePath).getName(); // Get only the filename

        System.out.printf("Results for file '%s': %.2f%% of words are correct (%d correct out of %d total words)%n", 
                          fileName, percentage, result.correctWordsCount, result.totalWords);

        System.out.println("Correct words found: " + result.correctWordsFound);
        System.out.println("Wrong words found: " + result.wrongWordsFound);
        System.out.println();
    }

    // Display overall statistics
    private void displayOverallStatistics() {
        System.out.printf("Total files scanned: %d%n", totalFilesScanned);
        System.out.printf("Files correct: %d%n", totalFilesScanned-infectedFiles);
        System.out.printf("Files infected: %d%n", infectedFiles);
    }
}
