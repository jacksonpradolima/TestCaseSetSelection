package br.ufpr.inf.cbiogres.reader;

import br.ufpr.inf.cbiogres.pojo.Mutant;
import br.ufpr.inf.cbiogres.pojo.TestCase;
import br.ufpr.inf.cbiogres.pojo.TestCaseMutant;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Reader {

    private String separator;
    private InputStream file;
    private List<Mutant> mutants;
    private List<TestCase> testCases;

    public Reader(String filePath, String separator) throws FileNotFoundException {
        this(new FileInputStream(filePath), separator);
    }

    public Reader(InputStream file, String separator) {
        this.file = file;
        this.separator = separator;
    }

    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public List<Mutant> getMutants() {
        return mutants;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    private List<TestCase> buildTestCaseList(String fileLine) {
        List<TestCase> list = new ArrayList<>();

        List<String> asList = Arrays.asList(fileLine.split(separator));
        int idCount = 0;
        for (String string : asList) {
            TestCase testCase = new TestCase(idCount++);
            testCase.setDescription(string);
            testCase.setTestCaseMutantList(new ArrayList<>());
            list.add(testCase);
        }
        return list;
    }

    public void read() {
        try (Scanner scanner = new Scanner(file)) {

            //Test Case IDs
            String line = scanner.nextLine();

            //Build Test Case objects
            testCases = buildTestCaseList(line);

            mutants = new ArrayList<>();

            //While there is something to read
            int mutantId = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                //Tokenize
                List<String> asList = Arrays.asList(line.split(separator));
                Iterator<String> tokenIterator = asList.iterator();
                //First value is the Mutant ID

                Mutant mutant = new Mutant(mutantId++);
                mutant.setDescription(tokenIterator.next());
                mutant.setTestCaseMutantList(new ArrayList<>());
                mutants.add(mutant);
                Iterator<TestCase> testCaseIterator = testCases.iterator();
                while (tokenIterator.hasNext() && testCaseIterator.hasNext()) {
                    Boolean value = Boolean.valueOf(tokenIterator.next());
                    TestCase testCase = testCaseIterator.next();
                    TestCaseMutant testCaseMutant = new TestCaseMutant(testCase, mutant, value);
                    testCase.getTestCaseMutantList().add(testCaseMutant);
                    mutant.getTestCaseMutantList().add(testCaseMutant);
                }

            }
        }
    }
}
