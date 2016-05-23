package br.ufpr.inf.cbiogres.pojo;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private long id = Long.MAX_VALUE;
    private List<ProductMutant> productMutantList = new ArrayList<>();

    public Product(Product product){
        this.id = product.getId();
        productMutantList.addAll(product.getProductMutantList());
    }
    
    public Product() {
    }

    public Product(long id) {
        this.id = id;
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

    public int getKillCount() {
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
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public Product getClone() {
        Product clone = new Product();
        clone.setId(this.id);
        clone.setProductMutantList(this.getProductMutantList());
        return clone;
    }
}
