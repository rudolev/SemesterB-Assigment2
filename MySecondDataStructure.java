import java.util.LinkedList;

class MySecondDataStructure {
    private LinkedList<Product> products;
    private int[] qualityCount;
    private int[] priceIncrement;
    private int totalQuality;
    private int totalCount;
    private Product maxPriceProduct;

    public MySecondDataStructure(int N) {
        products = new LinkedList<>();
        qualityCount = new int[6];
        priceIncrement = new int[6];
        totalQuality = 0;
        totalCount = 0;
        maxPriceProduct = null;
    }

    public void insert(Product product) {
        products.add(product);
        qualityCount[product.quality()]++;
        totalQuality += product.quality();
        totalCount++;

        if (maxPriceProduct == null || getEffectivePrice(product) > getEffectivePrice(maxPriceProduct)) {
            maxPriceProduct = product;
        }
    }

    public void findAndRemove(int id) {
        Product toRemove = null;

        for (Product p : products) {
            if (p.id() == id) {
                toRemove = p;
                break;
            }
        }

        if (toRemove != null) {
            products.remove(toRemove);
            qualityCount[toRemove.quality()]--;
            totalQuality -= toRemove.quality();
            totalCount--;

            if (maxPriceProduct != null && maxPriceProduct.id() == id) {
                maxPriceProduct = null;
                for (Product p : products) {
                    if (maxPriceProduct == null || getEffectivePrice(p) > getEffectivePrice(maxPriceProduct)) {
                        maxPriceProduct = p;
                    }
                }
            }
        }
    }

    public int medianQuality() {
        if (totalCount == 0) return -1;

        int mid = (totalCount + 1) / 2;
        int sum = 0;
        for (int i = 0; i <= 5; i++) {
            sum += qualityCount[i];
            if (sum >= mid) return i;
        }
        return -1;
    }

    public double avgQuality() {
        if (totalCount == 0) return -1;
        return (double) totalQuality / totalCount;
    }

    public void raisePrice(int raise, int quality) {
        priceIncrement[quality] += raise;

        if (maxPriceProduct != null && maxPriceProduct.quality() == quality) {
            maxPriceProduct = null;
            for (Product p : products) {
                if (maxPriceProduct == null || getEffectivePrice(p) > getEffectivePrice(maxPriceProduct)) {
                    maxPriceProduct = p;
                }
            }
        }
    }

    public Product mostExpensive() {
        if (maxPriceProduct == null) return null;

        Product p = maxPriceProduct;
        return new Product(p.id(), p.quality(), getEffectivePrice(p), p.name());
    }

    private int getEffectivePrice(Product p) {
        return p.price() + priceIncrement[p.quality()];
    }
}