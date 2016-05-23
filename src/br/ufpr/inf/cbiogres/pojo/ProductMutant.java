package br.ufpr.inf.cbiogres.pojo;

import java.util.Objects;

public class ProductMutant {

    private Product product;
    private Mutant mutant;
    private boolean killed;

    public ProductMutant(Product product, Mutant mutant, boolean killed) {
        this.product = product;
        this.mutant = mutant;
        this.killed = killed;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Mutant getMutant() {
        return mutant;
    }

    public void setMutant(Mutant mutant) {
        this.mutant = mutant;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.mutant);
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
        final ProductMutant other = (ProductMutant) obj;
        if (!Objects.equals(this.mutant, other.mutant)) {
            return false;
        }
        return true;
    }
}
