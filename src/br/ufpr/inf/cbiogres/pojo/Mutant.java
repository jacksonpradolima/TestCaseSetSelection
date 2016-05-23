package br.ufpr.inf.cbiogres.pojo;

import java.util.ArrayList;
import java.util.List;

public class Mutant {

    private long id;
    private List<ProductMutant> productMutantList = new ArrayList<>();

    public Mutant(long id) {
        this.id = id;
    }

    public Mutant() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductMutant> getProductMutantList() {
        return productMutantList;
    }

    public void setProductMutantList(List<ProductMutant> productMutantList) {
        this.productMutantList = productMutantList;
    }

    public int getKilledByCount() {
        int count = 0;
        for (ProductMutant productMutant : productMutantList) {
            if (productMutant.isKilled()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mutant other = (Mutant) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
