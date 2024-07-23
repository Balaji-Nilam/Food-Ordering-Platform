//I had used Java to complete this assignment

import java.util.*;
import java.util.stream.Collectors;

// Blueprint for Product
class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private Category category;
    private double discount;

    public Product(int id, String name, double price, int quantity, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discount = 0.0;
    }

    public Product(int id, String name, double price, int quantity, Category category, double discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.discount = discount;
    }

    public double getFinalPrice() {
        return price - discount;
    }

    public String getPriceDisplay() {
        if (category != null && category.getName().equals("Vintage Electronics")) {
            return String.format("$%.2f (Vintage Special)", getFinalPrice());
        }
        return String.format("$%.2f", getFinalPrice());
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category=" + category +
                ", discount=" + discount +
                '}';
    }
}

// Category Hierarchy
class Category {
    private String name;
    private Category parent;
    private List<Category> subcategories;

    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
    }

    public void addSubcategory(Category subcategory) {
        subcategories.add(subcategory);
        subcategory.setParent(this);
    }

    private void setParent(Category parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", parent=" + (parent != null ? parent.getName() : "None") +
                '}';
    }
}

// Main class with search and filter capabilities
public class Store {
    private List<Product> products;

    public Store() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return products.stream()
                .filter(p -> p.getFinalPrice() >= minPrice && p.getFinalPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public Map<Category, List<Product>> groupByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    public static void main(String[] args) {
        // Categories
        Category electronics = new Category("Electronics");
        Category stamps = new Category("Stamps");
        Category coins = new Category("Coins");
        Category vintageComputers = new Category("Vintage Computers");

        electronics.addSubcategory(vintageComputers);

        // Products
        Product product1 = new Product(1, "Vintage Laptop", 199.99, 5, vintageComputers);
        Product product2 = new Product(2, "Modern Laptop", 999.99, 10, electronics, 50);
        Product product3 = new Product(3, "Rare Stamp", 49.99, 50, stamps);
        Product product4 = new Product(4, "Ancient Coin", 99.99, 20, coins);

        // Store
        Store store = new Store();
        store.addProduct(product1);
        store.addProduct(product2);
        store.addProduct(product3);
        store.addProduct(product4);
        Scanner sc= new Scanner(System.in);
        boolean next=true;
        while(next){
            System.out.println("Enter your choice: \n1. Search by name \n2.See all Products\n3.Search by price\n4. Categories\n5.Exit");
            int choice=sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the name of the product(laptop,coin,stamp): ");
                    String search_item=sc.next();
                    System.out.println("Search results for"+search_item+": " + store.searchByName(search_item));
                    break;
                case 2:
                    System.out.println("All products: " );
                    System.out.println(product1.getName() + ": " + product1.getPriceDisplay());
                    System.out.println(product2.getName() + ": " + product2.getPriceDisplay());
                    System.out.println(product3.getName() + ": " + product3.getPriceDisplay());
                    System.out.println(product4.getName() + ": " + product4.getPriceDisplay());
                    break;
                case 3:
                    System.out.println("Enter Starting price:");
                    int start=sc.nextInt();
                    System.out.println("Enter Maximum price:");
                    int max=sc.nextInt();
                    System.out.println("Products priced between $"+start+" and $"+max+": " + store.filterByPriceRange(start,max));
                    break;
                case 4:
                    System.out.println("Products grouped by category: " + store.groupByCategory());
                    break;
                case 5:
                    next=false;
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }
}
