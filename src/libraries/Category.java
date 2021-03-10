package libraries;

public class Category implements java.io.Serializable {
    public String categoryName;
    
    public void setCategoryName(String cat) {
        this.categoryName = cat;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
}