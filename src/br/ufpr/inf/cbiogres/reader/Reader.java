package br.ufpr.inf.cbiogres.reader;

import br.ufpr.inf.cbiogres.pojo.Mutant;
import br.ufpr.inf.cbiogres.pojo.Product;
import br.ufpr.inf.cbiogres.pojo.ProductMutant;
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
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    private List<Product> buildProductList(String fileLine) {
        List<Product> list = new ArrayList<>();

        List<String> asList = Arrays.asList(fileLine.split(separator));
        for (String string : asList) {
            try {
                Long valueOf = Long.valueOf(string);
                Product product = new Product(valueOf);
                product.setProductMutantList(new ArrayList<>());
                list.add(product);
            } catch (NumberFormatException nfe) {
                //Skip if not number;
                continue;
            }
        }
        return list;
    }

    public void read() {
        try (Scanner scanner = new Scanner(file)) {

            //Products IDs
            String line = scanner.nextLine();

            //Build product objects
            products = buildProductList(line);

            mutants = new ArrayList<>();

            //While there is something to read
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                //Tokenize
                List<String> asList = Arrays.asList(line.split(separator));
                Iterator<String> tokenIterator = asList.iterator();
                //First value is the Mutant ID

                Long id = Long.valueOf(tokenIterator.next());
                Mutant mutant = new Mutant(id);
                mutant.setProductMutantList(new ArrayList<>());
                mutants.add(mutant);
                Iterator<Product> productIterator = products.iterator();
                while (tokenIterator.hasNext() && productIterator.hasNext()) {
                    Boolean value = Boolean.valueOf(tokenIterator.next());
                    Product product = productIterator.next();
                    ProductMutant productMutant = new ProductMutant(product, mutant, value);
                    product.getProductMutantList().add(productMutant);
                    mutant.getProductMutantList().add(productMutant);
                }

            }
        }
    }
}
